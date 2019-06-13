// Copyright (c) 2018, Yubico AB
// All rights reserved.
//
// Redistribution and use in source and binary forms, with or without
// modification, are permitted provided that the following conditions are met:
//
// 1. Redistributions of source code must retain the above copyright notice, this
//    list of conditions and the following disclaimer.
//
// 2. Redistributions in binary form must reproduce the above copyright notice,
//    this list of conditions and the following disclaimer in the documentation
//    and/or other materials provided with the distribution.
//
// THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
// AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
// IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
// DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE
// FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
// DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
// SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
// CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
// OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
// OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.

package demo.webauthn;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.io.Closeables;
import com.yubico.internal.util.CertificateParser;
import com.yubico.internal.util.ExceptionUtil;
import com.yubico.internal.util.WebAuthnCodecs;
import com.yubico.util.Either;
import com.yubico.webauthn.*;
import com.yubico.webauthn.attestation.*;
import com.yubico.webauthn.attestation.resolver.CompositeAttestationResolver;
import com.yubico.webauthn.attestation.resolver.CompositeTrustResolver;
import com.yubico.webauthn.attestation.resolver.SimpleAttestationResolver;
import com.yubico.webauthn.attestation.resolver.SimpleTrustResolverWithEquality;
import com.yubico.webauthn.data.*;
import com.yubico.webauthn.exception.AssertionFailedException;
import com.yubico.webauthn.exception.RegistrationFailedException;
import com.yubico.webauthn.extension.appid.AppId;
import com.yubico.webauthn.extension.appid.InvalidAppIdException;
import demo.webauthn.data.*;
import demo.webauthn.testdata.ServerAuthenticatorAttestationResponse;
import demo.webauthn.testdata.ServerPublicKeyCredentialCreationOptionsRequest;
import demo.webauthn.testdata.ServerPublicKeyCredentialCreationOptionsResponse;
import demo.webauthn.testdata.ServerResponse;
import lombok.NonNull;
import lombok.Value;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.security.SecureRandom;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.time.Clock;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.function.Supplier;

public class WebAuthnServer {
    private static final Logger logger = LoggerFactory.getLogger(WebAuthnServer.class);
    private static final SecureRandom random = new SecureRandom();

    private static final String RP_NAME = "WAY_FIDO2_SERVER";
    private static final String TAG = "WebAuthnServer";
    private static final String PREVIEW_METADATA_PATH = "/preview-metadata.json";

    private final Cache<ByteArray, AssertionRequestWrapper> assertRequestStorage;
    private final Cache<ByteArray, RegistrationRequest> registerRequestStorage;
    private final RegistrationStorage userStorage;
    private final Cache<String, RelyingParty> rpStorage = newCache();
    private final Cache<AssertionRequestWrapper, AuthenticatedAction> authenticatedActions = newCache();


    private final TrustResolver trustResolver = new CompositeTrustResolver(Arrays.asList(
            StandardMetadataService.createDefaultTrustResolver(),
            createExtraTrustResolver()
    ));

    private final MetadataService metadataService = new StandardMetadataService(
            new CompositeAttestationResolver(Arrays.asList(
                    StandardMetadataService.createDefaultAttestationResolver(trustResolver),
                    createExtraMetadataResolver(trustResolver)
            ))
    );

    private final Clock clock = Clock.systemDefaultZone();
    private final ObjectMapper jsonMapper = WebAuthnCodecs.json();

    private final RelyingParty rp;
    private PublicKeyCredentialCreationOptions publicKeyCredentialCreationOptionsCache = null;

    public WebAuthnServer() throws InvalidAppIdException, CertificateException {
        this(new InMemoryRegistrationStorage(), newCache(), newCache(), Config.getRpIdentity(), Config.getOrigins(),
                Config.getAppId());
    }

    public WebAuthnServer(RegistrationStorage userStorage,
                          Cache<ByteArray, RegistrationRequest> registerRequestStorage, Cache<ByteArray,
            AssertionRequestWrapper> assertRequestStorage, RelyingPartyIdentity rpIdentity, Set<String> origins,
                          Optional<AppId> appId) throws
            InvalidAppIdException, CertificateException {
        this.userStorage = userStorage;
        this.registerRequestStorage = registerRequestStorage;
        this.assertRequestStorage = assertRequestStorage;

        rp = RelyingParty.builder()
                .identity(rpIdentity)
                .credentialRepository(this.userStorage)
                .origins(origins)
                .attestationConveyancePreference(Optional.of(AttestationConveyancePreference.DIRECT))
                .metadataService(Optional.of(metadataService))
                .allowUnrequestedExtensions(true)
                .allowUntrustedAttestation(true)
                .validateSignatureCounter(true)
                .appId(appId)
                .build();
    }

    private static ByteArray generateRandom(int length) {
        byte[] bytes = new byte[length];
        random.nextBytes(bytes);
        return new ByteArray(bytes);
    }

    private static MetadataObject readPreviewMetadata() {
        InputStream is = WebAuthnServer.class.getResourceAsStream(PREVIEW_METADATA_PATH);
        try {
            return WebAuthnCodecs.json().readValue(is, MetadataObject.class);
        } catch (IOException e) {
            throw ExceptionUtil.wrapAndLog(logger, "Failed to read metadata from " + PREVIEW_METADATA_PATH, e);
        } finally {
            Closeables.closeQuietly(is);
        }
    }

    /**
     * Create a {@link TrustResolver} that accepts attestation certificates that are directly recognised as trust
     * anchors.
     */
    private static TrustResolver createExtraTrustResolver() {
        try {
            MetadataObject metadata = readPreviewMetadata();
            return new SimpleTrustResolverWithEquality(metadata.getParsedTrustedCertificates());
        } catch (CertificateException e) {
            throw ExceptionUtil.wrapAndLog(logger, "Failed to read trusted certificate(s)", e);
        }
    }

    /**
     * Create a {@link AttestationResolver} with additional metadata for unreleased YubiKey Preview devices.
     */
    private static AttestationResolver createExtraMetadataResolver(TrustResolver trustResolver) {
        try {
            MetadataObject metadata = readPreviewMetadata();
            return new SimpleAttestationResolver(Collections.singleton(metadata), trustResolver);
        } catch (CertificateException e) {
            throw ExceptionUtil.wrapAndLog(logger, "Failed to read trusted certificate(s)", e);
        }
    }

    private static <K, V> Cache<K, V> newCache() {
        return CacheBuilder.newBuilder()
                .maximumSize(100)
                .expireAfterAccess(10, TimeUnit.MINUTES)
                .build();
    }

    private String writeJson(Object o) throws JsonProcessingException {
        return jsonMapper.writeValueAsString(o);
    }

    /**
     * FIDO2测试接口1：
     *
     * @param optionsRequest
     * @return
     */
    public String startRegTest(ServerPublicKeyCredentialCreationOptionsRequest optionsRequest) {

        PublicKeyCredentialCreationOptions publicKeyCredentialCreationOptions = null;
        ServerPublicKeyCredentialCreationOptionsResponse regResponse = null;

        /*if (userStorage.getRegistrationsByUsername(optionsRequest.getUsername()).isEmpty()) {*/
            publicKeyCredentialCreationOptions = rp.startRegistration(
                    StartRegistrationOptions.builder()
                            .user(
                                    UserIdentity.builder()
                                            .name(optionsRequest.getUsername())
                                            .displayName(optionsRequest.getDisplayName())
                                            .id(generateRandom(32))
                                            .build()
                            ).authenticatorSelection(
                            Optional.of(
                                    AuthenticatorSelectionCriteria.builder()
                                            .requireResidentKey(
                                                    !Optional.ofNullable(optionsRequest.getAuthenticatorSelection()).isPresent() ? false :
                                                            optionsRequest.getAuthenticatorSelection().isRequireResidentKey()
                                            ).build()
                            )
                    ).build()
            );
            rpStorage.put(optionsRequest.getUsername(), rp);
            publicKeyCredentialCreationOptionsCache = publicKeyCredentialCreationOptions;
            regResponse = new ServerPublicKeyCredentialCreationOptionsResponse(publicKeyCredentialCreationOptions);
            /* 替换成请求中的数据： */
            regResponse.setAttestation(optionsRequest.getAttestation());
            regResponse.setErrorMessage("");
            regResponse.setStatus("ok");
            String response = null;
            try {
                response = writeJson(regResponse);
                //只有测试的第一个请求才需要此替换
                response = response.replace("{}", "{\"example.extension\": true}");
                return response;
            } catch (JsonProcessingException e) {
                e.printStackTrace();
                return JSON.toJSONString(new ServerResponse("", "JSON转换异常"));
            }
        /*} else {
            logger.warn("The username \"" + optionsRequest.getUsername() + "\" is already registered.");
            regResponse.setStatus("failed");
            regResponse.setErrorMessage("The username \"" + optionsRequest.getUsername() + "\" is already registered.");
            return JSON.toJSONString(regResponse);
        }*/
    }


    public Either<String, RegistrationRequest> startRegistration(
            @NonNull String username,
            @NonNull String displayName,
            Optional<String> credentialNickname,
            boolean requireResidentKey
    ) {
        logger.trace("startRegistration username: {}, credentialNickname: {}", username, credentialNickname);

        if (userStorage.getRegistrationsByUsername(username).isEmpty()) {
            RegistrationRequest request = new RegistrationRequest(
                    username,
                    credentialNickname,
                    generateRandom(32),
                    rp.startRegistration(
                            StartRegistrationOptions.builder()
                                    .user(UserIdentity.builder()
                                            .name(username)
                                            .displayName(displayName)
                                            .id(generateRandom(32))
                                            .build()
                                    )
                                    .authenticatorSelection(AuthenticatorSelectionCriteria.builder()
                                            .requireResidentKey(requireResidentKey)
                                            .build()
                                    )
                                    .build()
                    )
            );
            registerRequestStorage.put(request.getRequestId(), request);
            return Either.right(request);
        } else {
            return Either.left("The username \"" + username + "\" is already registered.");
        }
    }

    public <T> Either<List<String>, AssertionRequestWrapper> startAddCredential(
            @NonNull String username,
            Optional<String> credentialNickname,
            boolean requireResidentKey,
            Function<RegistrationRequest, Either<List<String>, T>> whenAuthenticated
    ) {
        logger.trace("startAddCredential username: {}, credentialNickname: {}, requireResidentKey: {}", username,
                credentialNickname, requireResidentKey);

        if (username == null || username.isEmpty()) {
            return Either.left(Collections.singletonList("username must not be empty."));
        }

        Collection<CredentialRegistration> registrations = userStorage.getRegistrationsByUsername(username);

        if (registrations.isEmpty()) {
            return Either.left(Collections.singletonList("The username \"" + username + "\" is not registered."));
        } else {
            final UserIdentity existingUser = registrations.stream().findAny().get().getUserIdentity();

            AuthenticatedAction<T> action = (SuccessfulAuthenticationResult result) -> {
                RegistrationRequest request = new RegistrationRequest(
                        username,
                        credentialNickname,
                        generateRandom(32),
                        rp.startRegistration(
                                StartRegistrationOptions.builder()
                                        .user(existingUser)
                                        .authenticatorSelection(AuthenticatorSelectionCriteria.builder()
                                                .requireResidentKey(requireResidentKey)
                                                .build()
                                        )
                                        .build()
                        )
                );
                registerRequestStorage.put(request.getRequestId(), request);

                return whenAuthenticated.apply(request);
            };

            return startAuthenticatedAction(Optional.of(username), action);
        }
    }

    public String testFinishReg(ServerAuthenticatorAttestationResponse response) {
        /**
         * 认证器返回数据校验
         */
        if (!Optional.ofNullable(response.getId()).isPresent()) {
            return JSON.toJSONString(new ServerResponse("failed", "id can not be null!"));
        }
        if (!Optional.ofNullable(response.getRawId()).isPresent()) {
            return JSON.toJSONString(new ServerResponse("failed", "rawId can not be null!"));
        }
        if (!response.getId().equals(response.getRawId())) {
            return JSON.toJSONString(new ServerResponse("failed", "id is not equals to rawId"));
        }
        Map<String, Object> credentialMap = new HashMap<>();
        credentialMap.put("type", response.getType());
        credentialMap.put("id", response.getId());
        credentialMap.put("response", response.getResponse());
        credentialMap.put("clientExtensionResults", new HashMap<>());

        UserIdentity user = publicKeyCredentialCreationOptionsCache.getUser();
        PublicKeyCredential<AuthenticatorAttestationResponse, ClientRegistrationExtensionOutputs> credential;
        try {
            credential = PublicKeyCredential.parseRegistrationResponseJson(JSON.toJSONString(credentialMap));
        } catch (IOException e) {
            return JSON.toJSONString(new ServerResponse("failed", "AuthenticatorAttestationResponse Error!"));
        }

        try {
            AttestationObject attestationObject =
                    new AttestationObject(credential.getResponse().getAttestationObject());
            JsonNode algNode = attestationObject.getAttestationStatement().get("alg");
            if (!Optional.ofNullable(algNode).isPresent()) {
                return JSON.toJSONString(new ServerResponse("failed", "attStmt.alg is missing!"));
            }
            if (!attestationObject.getAttestationStatement().get("alg").isNumber()) {
                return JSON.toJSONString(new ServerResponse("failed", "attStmt.alg is not Number!"));
            }
            List<Long> algList = new ArrayList();
            algList.add(-7L);
            algList.add(-257L);
            algList.add(-65535L);
            if (!algList.contains(attestationObject.getAttestationStatement().get("alg").asLong())){
                return JSON.toJSONString(new ServerResponse("failed", "attStmt.alg is not match Alg in metadata!"));
            }
        } catch (IOException e) {
            e.printStackTrace();
            return JSON.toJSONString(new ServerResponse("failed", e.getMessage()));
        }
        try {
            com.yubico.webauthn.RegistrationResult registration = rp.finishRegistration(
                    FinishRegistrationOptions.builder()
                            .request(publicKeyCredentialCreationOptionsCache)
                            .response(credential)
                            .build()
            );
            /* 注册数据添加到缓存 */
            addRegistration(
                    publicKeyCredentialCreationOptionsCache.getUser(),
                    Optional.ofNullable(publicKeyCredentialCreationOptionsCache.getUser().getDisplayName()),
                    credential.getResponse().getAttestation().getAuthenticatorData().getSignatureCounter(),
                    registration
            );
            return JSON.toJSONString(new ServerResponse("ok", ""));
        } catch (RegistrationFailedException e) {
            e.printStackTrace();
            return JSON.toJSONString(new ServerResponse("failed", e.getMessage()));
        }
    }


    public Either<List<String>, SuccessfulRegistrationResult> finishRegistration(String responseJson) {
        logger.trace("finishRegistration responseJson: {}", responseJson);
        RegistrationResponse response = null;
        try {
            response = jsonMapper.readValue(responseJson, RegistrationResponse.class);
        } catch (IOException e) {
            logger.error("JSON error in finishRegistration; responseJson: {}", responseJson, e);
            return Either.left(Arrays.asList("Registration failed!", "Failed to decode response object.",
                    e.getMessage()));
        }

        RegistrationRequest request = registerRequestStorage.getIfPresent(response.getRequestId());
        registerRequestStorage.invalidate(response.getRequestId());

        if (request == null) {
            logger.debug("fail finishRegistration responseJson: {}", responseJson);
            return Either.left(Arrays.asList("Registration failed!", "No such registration in progress."));
        } else {
            try {
                com.yubico.webauthn.RegistrationResult registration = rp.finishRegistration(
                        FinishRegistrationOptions.builder()
                                .request(request.getPublicKeyCredentialCreationOptions())
                                .response(response.getCredential())
                                .build()
                );

                return Either.right(
                        new SuccessfulRegistrationResult(
                                request,
                                response,
                                addRegistration(
                                        request.getPublicKeyCredentialCreationOptions().getUser(),
                                        request.getCredentialNickname(),
                                        response,
                                        registration
                                ),
                                registration.isAttestationTrusted()
                        )
                );
            } catch (RegistrationFailedException e) {
                logger.debug("fail finishRegistration responseJson: {}", responseJson, e);
                return Either.left(Arrays.asList("Registration failed!", e.getMessage()));
            } catch (Exception e) {
                logger.error("fail finishRegistration responseJson: {}", responseJson, e);
                return Either.left(Arrays.asList("Registration failed unexpectedly; this is likely a bug.",
                        e.getMessage()));
            }
        }
    }

    public Either<List<String>, SuccessfulU2fRegistrationResult> finishU2fRegistration(String responseJson) {
        logger.trace("finishU2fRegistration responseJson: {}", responseJson);
        U2fRegistrationResponse response = null;
        try {
            response = jsonMapper.readValue(responseJson, U2fRegistrationResponse.class);
        } catch (IOException e) {
            logger.error("JSON error in finishU2fRegistration; responseJson: {}", responseJson, e);
            return Either.left(Arrays.asList("Registration failed!", "Failed to decode response object.",
                    e.getMessage()));
        }

        RegistrationRequest request = registerRequestStorage.getIfPresent(response.getRequestId());
        registerRequestStorage.invalidate(response.getRequestId());

        if (request == null) {
            logger.debug("fail finishU2fRegistration responseJson: {}", responseJson);
            return Either.left(Arrays.asList("Registration failed!", "No such registration in progress."));
        } else {

            try {
                ExceptionUtil.assure(
                        U2fVerifier.verify(rp.getAppId().get(), request, response),
                        "Failed to verify signature."
                );
            } catch (Exception e) {
                logger.debug("Failed to verify U2F signature.", e);
                return Either.left(Arrays.asList("Failed to verify signature.", e.getMessage()));
            }

            X509Certificate attestationCert = null;
            try {
                attestationCert =
                        CertificateParser.parseDer(response.getCredential().getU2fResponse().getAttestationCertAndSignature().getBytes());
            } catch (CertificateException e) {
                logger.error("Failed to parse attestation certificate: {}",
                        response.getCredential().getU2fResponse().getAttestationCertAndSignature(), e);
            }

            Optional<Attestation> attestation = Optional.empty();
            try {
                if (attestationCert != null) {
                    attestation =
                            Optional.of(metadataService.getAttestation(Collections.singletonList(attestationCert)));
                }
            } catch (CertificateEncodingException e) {
                logger.error("Failed to resolve attestation", e);
            }

            final U2fRegistrationResult result = U2fRegistrationResult.builder()
                    .keyId(PublicKeyCredentialDescriptor.builder().id(response.getCredential().getU2fResponse().getKeyHandle()).build())
                    .attestationTrusted(attestation.map(Attestation::isTrusted).orElse(false))
                    .publicKeyCose(WebAuthnCodecs.rawEcdaKeyToCose(response.getCredential().getU2fResponse().getPublicKey()))
                    .attestationMetadata(attestation)
                    .build();

            return Either.right(
                    new SuccessfulU2fRegistrationResult(
                            request,
                            response,
                            addRegistration(
                                    request.getPublicKeyCredentialCreationOptions().getUser(),
                                    request.getCredentialNickname(),
                                    0,
                                    result
                            ),
                            result.isAttestationTrusted(),
                            Optional.of(new AttestationCertInfo(response.getCredential().getU2fResponse().getAttestationCertAndSignature()))
                    )
            );
        }
    }

    public Either<List<String>, AssertionRequestWrapper> startAuthentication(Optional<String> username) {
        logger.trace("startAuthentication username: {}", username);

        if (username.isPresent() && userStorage.getRegistrationsByUsername(username.get()).isEmpty()) {
            return Either.left(Collections.singletonList("The username \"" + username.get() + "\" is not registered."));
        } else {
            AssertionRequestWrapper request = new AssertionRequestWrapper(
                    generateRandom(32),
                    rp.startAssertion(
                            StartAssertionOptions.builder()
                                    .username(username)
                                    .build()
                    )
            );

            assertRequestStorage.put(request.getRequestId(), request);

            return Either.right(request);
        }
    }

    public Either<List<String>, SuccessfulAuthenticationResult> finishAuthentication(String responseJson) {
        logger.trace("finishAuthentication responseJson: {}", responseJson);

        final AssertionResponse response;
        try {
            response = jsonMapper.readValue(responseJson, AssertionResponse.class);
        } catch (IOException e) {
            logger.debug("Failed to decode response object", e);
            return Either.left(Arrays.asList("Assertion failed!", "Failed to decode response object.", e.getMessage()));
        }

        AssertionRequestWrapper request = assertRequestStorage.getIfPresent(response.getRequestId());
        assertRequestStorage.invalidate(response.getRequestId());

        if (request == null) {
            return Either.left(Arrays.asList("Assertion failed!", "No such assertion in progress."));
        } else {
            try {
                AssertionResult result = rp.finishAssertion(
                        FinishAssertionOptions.builder()
                                .request(request.getRequest())
                                .response(response.getCredential())
                                .build()
                );

                if (result.isSuccess()) {
                    try {
                        userStorage.updateSignatureCount(result);
                    } catch (Exception e) {
                        logger.error(
                                "Failed to update signature count for user \"{}\", credential \"{}\"",
                                result.getUsername(),
                                response.getCredential().getId(),
                                e
                        );
                    }

                    return Either.right(
                            new SuccessfulAuthenticationResult(
                                    request,
                                    response,
                                    userStorage.getRegistrationsByUsername(result.getUsername()),
                                    result.getWarnings()
                            )
                    );
                } else {
                    return Either.left(Collections.singletonList("Assertion failed: Invalid assertion."));
                }
            } catch (AssertionFailedException e) {
                logger.debug("Assertion failed", e);
                return Either.left(Arrays.asList("Assertion failed!", e.getMessage()));
            } catch (Exception e) {
                logger.error("Assertion failed", e);
                return Either.left(Arrays.asList("Assertion failed unexpectedly; this is likely a bug.",
                        e.getMessage()));
            }
        }
    }

    public Either<List<String>, AssertionRequestWrapper> startAuthenticatedAction(Optional<String> username,
                                                                                  AuthenticatedAction<?> action) {
        return startAuthentication(username)
                .map(request -> {
                    synchronized (authenticatedActions) {
                        authenticatedActions.put(request, action);
                    }
                    return request;
                });
    }

    public Either<List<String>, ?> finishAuthenticatedAction(String responseJson) {
        return finishAuthentication(responseJson)
                .flatMap(result -> {
                    AuthenticatedAction<?> action = authenticatedActions.getIfPresent(result.request);
                    authenticatedActions.invalidate(result.request);
                    if (action == null) {
                        return Either.left(Collections.singletonList(
                                "No action was associated with assertion request ID: " + result.getRequest().getRequestId()
                        ));
                    } else {
                        return action.apply(result);
                    }
                });
    }

    public <T> Either<List<String>, AssertionRequestWrapper> deregisterCredential(String username,
                                                                                  ByteArray credentialId,
                                                                                  Function<CredentialRegistration, T> resultMapper) {
        logger.trace("deregisterCredential username: {}, credentialId: {}", username, credentialId);

        if (username == null || username.isEmpty()) {
            return Either.left(Collections.singletonList("Username must not be empty."));
        }

        if (credentialId == null || credentialId.getBytes().length == 0) {
            return Either.left(Collections.singletonList("Credential ID must not be empty."));
        }

        AuthenticatedAction<T> action = (SuccessfulAuthenticationResult result) -> {
            Optional<CredentialRegistration> credReg = userStorage.getRegistrationByUsernameAndCredentialId(username,
                    credentialId);

            if (credReg.isPresent()) {
                userStorage.removeRegistrationByUsername(username, credReg.get());
                return Either.right(resultMapper.apply(credReg.get()));
            } else {
                return Either.left(Collections.singletonList("Credential ID not registered:" + credentialId));
            }
        };

        return startAuthenticatedAction(Optional.of(username), action);
    }

    public <T> Either<List<String>, T> deleteAccount(String username, Supplier<T> onSuccess) {
        logger.trace("deleteAccount username: {}", username);

        if (username == null || username.isEmpty()) {
            return Either.left(Collections.singletonList("Username must not be empty."));
        }

        boolean removed = userStorage.removeAllRegistrations(username);

        if (removed) {
            return Either.right(onSuccess.get());
        } else {
            return Either.left(Collections.singletonList("Username not registered:" + username));
        }
    }


    /**
     * add by shengquan
     * @return
     */
    private CredentialRegistration addRegistration(
            UserIdentity userIdentity,
            Optional<String> nickname,
            long signatureCounter,
            RegistrationResult result
    ) {
        return addRegistration(
                userIdentity,
                nickname,
                signatureCounter,
                RegisteredCredential.builder()
                        .credentialId(result.getKeyId().getId())
                        .userHandle(userIdentity.getId())
                        .publicKeyCose(result.getPublicKeyCose())
                        .signatureCount(signatureCounter)
                        .build(),
                result.getAttestationMetadata()
        );
    }

    private CredentialRegistration addRegistration(
            UserIdentity userIdentity,
            Optional<String> nickname,
            RegistrationResponse response,
            RegistrationResult result
    ) {
        return addRegistration(
                userIdentity,
                nickname,
                response.getCredential().getResponse().getAttestation().getAuthenticatorData().getSignatureCounter(),
                RegisteredCredential.builder()
                        .credentialId(result.getKeyId().getId())
                        .userHandle(userIdentity.getId())
                        .publicKeyCose(result.getPublicKeyCose())
                        .signatureCount(response.getCredential().getResponse().getParsedAuthenticatorData().getSignatureCounter())
                        .build(),
                result.getAttestationMetadata()
        );
    }

    private CredentialRegistration addRegistration(
            UserIdentity userIdentity,
            Optional<String> nickname,
            long signatureCount,
            U2fRegistrationResult result
    ) {
        return addRegistration(
                userIdentity,
                nickname,
                signatureCount,
                RegisteredCredential.builder()
                        .credentialId(result.getKeyId().getId())
                        .userHandle(userIdentity.getId())
                        .publicKeyCose(result.getPublicKeyCose())
                        .signatureCount(signatureCount)
                        .build(),
                result.getAttestationMetadata()
        );
    }

    private CredentialRegistration addRegistration(
            UserIdentity userIdentity,
            Optional<String> nickname,
            long signatureCount,
            RegisteredCredential credential,
            Optional<Attestation> attestationMetadata
    ) {
        CredentialRegistration reg = CredentialRegistration.builder()
                .userIdentity(userIdentity)
                .credentialNickname(nickname)
                .registrationTime(clock.instant())
                .credential(credential)
                .signatureCount(signatureCount)
                .attestationMetadata(attestationMetadata)
                .build();

        logger.debug(
                "Adding registration: user: {}, nickname: {}, credential: {}",
                userIdentity,
                nickname,
                credential
        );
        userStorage.addRegistrationByUsername(userIdentity.getName(), reg);
        return reg;
    }

    @Value
    public static class SuccessfulRegistrationResult {
        final boolean success = true;
        RegistrationRequest request;
        RegistrationResponse response;
        CredentialRegistration registration;
        boolean attestationTrusted;
        Optional<AttestationCertInfo> attestationCert;

        public SuccessfulRegistrationResult(RegistrationRequest request, RegistrationResponse response,
                                            CredentialRegistration registration, boolean attestationTrusted) {
            this.request = request;
            this.response = response;
            this.registration = registration;
            this.attestationTrusted = attestationTrusted;
            attestationCert = Optional.ofNullable(
                    response.getCredential().getResponse().getAttestation().getAttestationStatement().get("x5c")
            ).map(certs -> certs.get(0))
                    .flatMap((JsonNode certDer) -> {
                        try {
                            return Optional.of(new ByteArray(certDer.binaryValue()));
                        } catch (IOException e) {
                            logger.error("Failed to get binary value from x5c element: {}", certDer, e);
                            return Optional.empty();
                        }
                    })
                    .map(AttestationCertInfo::new);
        }
    }

    @Value
    public static class AttestationCertInfo {
        final ByteArray der;
        final String text;

        public AttestationCertInfo(ByteArray certDer) {
            der = certDer;
            X509Certificate cert = null;
            try {
                cert = CertificateParser.parseDer(certDer.getBytes());
            } catch (CertificateException e) {
                logger.error("Failed to parse attestation certificate");
            }
            if (cert == null) {
                text = null;
            } else {
                text = cert.toString();
            }
        }
    }

    @Value
    public static class SuccessfulAuthenticationResult {
        final boolean success = true;
        AssertionRequestWrapper request;
        AssertionResponse response;
        Collection<CredentialRegistration> registrations;
        List<String> warnings;
    }

    @Value
    public class SuccessfulU2fRegistrationResult {
        final boolean success = true;
        final RegistrationRequest request;
        final U2fRegistrationResponse response;
        final CredentialRegistration registration;
        boolean attestationTrusted;
        Optional<AttestationCertInfo> attestationCert;
    }

}
