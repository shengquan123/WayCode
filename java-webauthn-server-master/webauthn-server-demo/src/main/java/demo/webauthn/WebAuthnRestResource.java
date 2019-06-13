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

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.yubico.util.Either;
import com.yubico.webauthn.data.ByteArray;
import com.yubico.webauthn.data.exception.Base64UrlException;
import com.yubico.internal.util.WebAuthnCodecs;
import com.yubico.webauthn.extension.appid.InvalidAppIdException;
import com.yubico.webauthn.meta.VersionInfo;
import demo.webauthn.data.AssertionRequestWrapper;
import demo.webauthn.data.RegistrationRequest;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.cert.CertificateException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import demo.webauthn.testdata.ServerPublicKeyCredentialCreationOptionsRequest;
import lombok.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Path("/v1")
@Produces(MediaType.APPLICATION_JSON)
public class WebAuthnRestResource {
    private static final Logger logger = LoggerFactory.getLogger(WebAuthnRestResource.class);

    private final WebAuthnServer server;
    private final ObjectMapper jsonMapper = WebAuthnCodecs.json();
    private final JsonNodeFactory jsonFactory = JsonNodeFactory.instance;

    public WebAuthnRestResource() throws InvalidAppIdException, CertificateException {
        this(new WebAuthnServer());
    }

    public WebAuthnRestResource(WebAuthnServer server) {
        this.server = server;
    }

    @Context
    private UriInfo uriInfo;

    private final class IndexResponse {
        public final Index actions = new Index();
        public final Info info = new Info();
        private IndexResponse() throws MalformedURLException {
        }
    }
    private final class Index {
        public final URL addCredential;
        public final URL authenticate;
        public final URL deleteAccount;
        public final URL deregister;
        public final URL register;


        public Index() throws MalformedURLException {
            addCredential = uriInfo.getAbsolutePathBuilder().path("action").path("add-credential").build().toURL();
            authenticate = uriInfo.getAbsolutePathBuilder().path("authenticate").build().toURL();
            deleteAccount = uriInfo.getAbsolutePathBuilder().path("delete-account").build().toURL();
            deregister = uriInfo.getAbsolutePathBuilder().path("action").path("deregister").build().toURL();
            register = uriInfo.getAbsolutePathBuilder().path("register").build().toURL();
        }
    }
    private final class Info {
        public final URL version;

        public Info() throws MalformedURLException {
            version = uriInfo.getAbsolutePathBuilder().path("version").build().toURL();
        }

    }

    @GET
    public Response index() throws IOException {
        return Response.ok(writeJson(new IndexResponse())).build();
    }

    private static final class VersionResponse {
        public final VersionInfo version = VersionInfo.getInstance();
    }
    @GET
    @Path("version")
    public Response version() throws JsonProcessingException {
        return Response.ok(writeJson(new VersionResponse())).build();
    }


    private final class StartRegistrationResponse {
        public final boolean success = true;
        public final RegistrationRequest request;
        public final StartRegistrationActions actions = new StartRegistrationActions();
        private StartRegistrationResponse(RegistrationRequest request) throws MalformedURLException {
            this.request = request;
        }
    }
    private final class StartRegistrationActions {
        public final URL finish = uriInfo.getAbsolutePathBuilder().path("finish").build().toURL();
        public final URL finishU2f = uriInfo.getAbsolutePathBuilder().path("finish-u2f").build().toURL();
        private StartRegistrationActions() throws MalformedURLException {
        }
    }

    @Path("/attestation/options")
    @POST
    public String startReg(@BeanParam ServerPublicKeyCredentialCreationOptionsRequest request) {

        System.out.println("请求数据：" + request);


        return "1";
    }

    @Path("register")
    @POST
    public Response startRegistration(
        @NonNull @FormParam("username") String username,
        @NonNull @FormParam("displayName") String displayName,
        @FormParam("credentialNickname") String credentialNickname,
        @FormParam("requireResidentKey") @DefaultValue("false") boolean requireResidentKey
    ) throws MalformedURLException {
        logger.trace("startRegistration username: {}, displayName: {}, credentialNickname: {}, requireResidentKey: {}", username, displayName, credentialNickname, requireResidentKey);
        Either<String, RegistrationRequest> result = server.startRegistration(
            username,
            displayName,
            Optional.ofNullable(credentialNickname),
            requireResidentKey
        );

        if (result.isRight()) {
            return startResponse("startRegistration", new StartRegistrationResponse(result.right().get()));
        } else {
            return messagesJson(
                Response.status(Status.BAD_REQUEST),
                result.left().get()
            );
        }
    }

    @Path("register/finish")
    @POST
    public Response finishRegistration(@NonNull String responseJson) {
        logger.trace("finishRegistration responseJson: {}", responseJson);
        Either<List<String>, WebAuthnServer.SuccessfulRegistrationResult> result = server.finishRegistration(responseJson);
        return finishResponse(
            result,
            "Attestation verification failed; further error message(s) were unfortunately lost to an internal server error.",
            "finishRegistration",
            responseJson
        );
    }

    @Path("register/finish-u2f")
    @POST
    public Response finishU2fRegistration(@NonNull String responseJson) {
        logger.trace("finishRegistration responseJson: {}", responseJson);
        Either<List<String>, WebAuthnServer.SuccessfulU2fRegistrationResult> result = server.finishU2fRegistration(responseJson);
        return finishResponse(
            result,
            "U2F registration failed; further error message(s) were unfortunately lost to an internal server error.",
            "finishU2fRegistration",
            responseJson
        );
    }

    private final class StartAuthenticationResponse {
        public final boolean success = true;
        public final AssertionRequestWrapper request;
        public final StartAuthenticationActions actions = new StartAuthenticationActions();
        private StartAuthenticationResponse(AssertionRequestWrapper request) throws MalformedURLException {
            this.request = request;
        }
    }
    private final class StartAuthenticationActions {
        public final URL finish = uriInfo.getAbsolutePathBuilder().path("finish").build().toURL();
        private StartAuthenticationActions() throws MalformedURLException {
        }
    }
    @Path("authenticate")
    @POST
    public Response startAuthentication(
        @FormParam("username") String username
    ) throws MalformedURLException {
        logger.trace("startAuthentication username: {}", username);
        Either<List<String>, AssertionRequestWrapper> request = server.startAuthentication(Optional.ofNullable(username));
        if (request.isRight()) {
            return startResponse("startAuthentication", new StartAuthenticationResponse(request.right().get()));
        } else {
            return messagesJson(Response.status(Status.BAD_REQUEST), request.left().get());
        }
    }

    @Path("authenticate/finish")
    @POST
    public Response finishAuthentication(@NonNull String responseJson) {
        logger.trace("finishAuthentication responseJson: {}", responseJson);

        Either<List<String>, WebAuthnServer.SuccessfulAuthenticationResult> result = server.finishAuthentication(responseJson);

        return finishResponse(
            result,
            "Authentication verification failed; further error message(s) were unfortunately lost to an internal server error.",
            "finishAuthentication",
            responseJson
        );
    }

    @Path("action/{action}/finish")
    @POST
    public Response finishAuthenticatedAction(
        @NonNull @PathParam("action") String action,
        @NonNull String responseJson
    ) {
        logger.trace("finishAuthenticatedAction: {}, responseJson: {}", action, responseJson);
        Either<List<String>, ?> mappedResult = server.finishAuthenticatedAction(responseJson);

        return finishResponse(
            mappedResult,
            "Action succeeded; further error message(s) were unfortunately lost to an internal server error.",
            "finishAuthenticatedAction",
            responseJson
        );
    }

    private final class StartAuthenticatedActionResponse {
        public final boolean success = true;
        public final AssertionRequestWrapper request;
        public final StartAuthenticatedActionActions actions = new StartAuthenticatedActionActions();
        private StartAuthenticatedActionResponse(AssertionRequestWrapper request) throws MalformedURLException {
            this.request = request;
        }
    }
    private final class StartAuthenticatedActionActions {
        public final URL finish = uriInfo.getAbsolutePathBuilder().path("finish").build().toURL();
        public final URL finishU2f = uriInfo.getAbsolutePathBuilder().path("finish-u2f").build().toURL();
        private StartAuthenticatedActionActions() throws MalformedURLException {
        }
    }

    @Path("action/add-credential")
    @POST
    public Response addCredential(
        @NonNull @FormParam("username") String username,
        @FormParam("credentialNickname") String credentialNickname,
        @FormParam("requireResidentKey") @DefaultValue("false") boolean requireResidentKey
    ) throws MalformedURLException {
        logger.trace("addCredential username: {}, credentialNickname: {}, requireResidentKey: {}", username, credentialNickname, requireResidentKey);

        Either<List<String>, AssertionRequestWrapper> result = server.startAddCredential(username, Optional.ofNullable(credentialNickname), requireResidentKey, (RegistrationRequest request) -> {
            try {
                return Either.right(new StartRegistrationResponse(request));
            } catch (MalformedURLException e) {
                logger.error("Failed to construct registration response", e);
                return Either.left(Arrays.asList("Failed to construct response. This is probably a bug in the server."));
            }
        });

        if (result.isRight()) {
            return startResponse("addCredential", new StartAuthenticatedActionResponse(result.right().get()));
        } else {
            return messagesJson(
                Response.status(Status.BAD_REQUEST),
                result.left().get()
            );
        }
    }

    @Path("action/add-credential/finish/finish")
    @POST
    public Response finishAddCredential(@NonNull String responseJson) {
        return finishRegistration(responseJson);
    }

    @Path("action/add-credential/finish/finish-u2f")
    @POST
    public Response finishU2fAddCredential(@NonNull String responseJson) {
        return finishU2fRegistration(responseJson);
    }

    @Path("action/deregister")
    @POST
    public Response deregisterCredential(
        @NonNull @FormParam("username") String username,
        @NonNull @FormParam("credentialId") String credentialIdBase64
    ) throws MalformedURLException {
        logger.trace("deregisterCredential username: {}, credentialId: {}", username, credentialIdBase64);

        final ByteArray credentialId;
        try {
            credentialId = ByteArray.fromBase64Url(credentialIdBase64);
        } catch (Base64UrlException e) {
            return messagesJson(
                Response.status(Status.BAD_REQUEST),
                "Credential ID is not valid Base64Url data: " + credentialIdBase64
            );
        }

        Either<List<String>, AssertionRequestWrapper> result = server.deregisterCredential(username, credentialId, (credentialRegistration -> {
            try {
                return ((ObjectNode) jsonFactory.objectNode()
                        .set("success", jsonFactory.booleanNode(true)))
                        .set("droppedRegistration", jsonMapper.readTree(writeJson(credentialRegistration)))
                ;
            } catch (IOException e) {
                logger.error("Failed to write response as JSON", e);
                throw new RuntimeException(e);
            }
        }));

        if (result.isRight()) {
            return startResponse("deregisterCredential", new StartAuthenticatedActionResponse(result.right().get()));
        } else {
            return messagesJson(
                Response.status(Status.BAD_REQUEST),
                result.left().get()
            );
        }
    }

    @Path("delete-account")
    @DELETE
    public Response deleteAccount(
        @NonNull @FormParam("username") String username
    ) {
        logger.trace("deleteAccount username: {}", username);

        Either<List<String>, JsonNode> result = server.deleteAccount(username, () ->
            ((ObjectNode) jsonFactory.objectNode()
                .set("success", jsonFactory.booleanNode(true)))
                .set("deletedAccount", jsonFactory.textNode(username))
        );

        if (result.isRight()) {
            return Response.ok(result.right().get().toString()).build();
        } else {
            return messagesJson(
                Response.status(Status.BAD_REQUEST),
                result.left().get()
            );
        }
    }

    private Response startResponse(String operationName, Object request) {
        try {
            String json = writeJson(request);
            logger.debug("{} JSON response: {}", operationName, json);
            return Response.ok(json).build();
        } catch (IOException e) {
            logger.error("Failed to encode response as JSON: {}", request, e);
            return jsonFail();
        }
    }

    private Response finishResponse(Either<List<String>, ?> result, String jsonFailMessage, String methodName, String responseJson) {
        if (result.isRight()) {
            try {
                return Response.ok(
                    writeJson(result.right().get())
                ).build();
            } catch (JsonProcessingException e) {
                logger.error("Failed to encode response as JSON: {}", result.right().get(), e);
                return messagesJson(
                    Response.ok(),
                    jsonFailMessage
                );
            }
        } else {
            logger.debug("fail {} responseJson: {}", methodName, responseJson);
            return messagesJson(
                Response.status(Status.BAD_REQUEST),
                result.left().get()
            );
        }
    }

    private Response jsonFail() {
        return Response.status(Status.INTERNAL_SERVER_ERROR)
            .entity("{\"messages\":[\"Failed to encode response as JSON\"]}")
            .build();
    }

    private Response messagesJson(ResponseBuilder response, String message) {
        return messagesJson(response, Arrays.asList(message));
    }

    private Response messagesJson(ResponseBuilder response, List<String> messages) {
        logger.debug("Encoding messages as JSON: {}", messages);
        try {
            return response.entity(
                writeJson(
                    jsonFactory.objectNode()
                        .set("messages", jsonFactory.arrayNode()
                            .addAll(messages.stream().map(jsonFactory::textNode).collect(Collectors.toList()))
                        )
                )
            ).build();
        } catch (JsonProcessingException e) {
            logger.error("Failed to encode messages as JSON: {}", messages, e);
            return jsonFail();
        }
    }

    private String writeJson(Object o) throws JsonProcessingException {
        if (uriInfo.getQueryParameters().keySet().contains("pretty")) {
            return jsonMapper.writerWithDefaultPrettyPrinter().writeValueAsString(o);
        } else {
            return jsonMapper.writeValueAsString(o);
        }
    }

}
