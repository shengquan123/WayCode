package demo.webauthn.testdata;

import com.yubico.webauthn.data.*;
import lombok.Data;
import lombok.ToString;

import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * @author:shengquan
 * @Date:2019/5/15 13:50
 */
@Data
@ToString
public class ServerPublicKeyCredentialCreationOptionsResponse extends ServerResponse {

    /**
     * private String status;
     * private String errorMessage;
     */
    private RelyingPartyIdentity rp;
    private UserIdentity user;
    private ByteArray challenge;
    private List<PublicKeyCredentialParameters> pubKeyCredParams;
    private Optional<Long> timeout = Optional.empty();
    private Optional<Set<PublicKeyCredentialDescriptor>> excludeCredentials = Optional.empty();
    private Optional<AuthenticatorSelectionCriteria> authenticatorSelection = Optional.empty();
    private AttestationConveyancePreference attestation = AttestationConveyancePreference.NONE;
    private RegistrationExtensionInputs extensions = RegistrationExtensionInputs.builder().build();

    public ServerPublicKeyCredentialCreationOptionsResponse() {
        super();
    }

    public ServerPublicKeyCredentialCreationOptionsResponse(PublicKeyCredentialCreationOptions publicKeyCredentialCreationOptions) {
        this.rp = publicKeyCredentialCreationOptions.getRp();
        this.user = publicKeyCredentialCreationOptions.getUser();
        this.challenge = publicKeyCredentialCreationOptions.getChallenge();
        this.pubKeyCredParams = publicKeyCredentialCreationOptions.getPubKeyCredParams();
        this.timeout = publicKeyCredentialCreationOptions.getTimeout();
        this.excludeCredentials = publicKeyCredentialCreationOptions.getExcludeCredentials();
        this.authenticatorSelection = publicKeyCredentialCreationOptions.getAuthenticatorSelection();
        this.attestation = publicKeyCredentialCreationOptions.getAttestation();
        this.extensions = publicKeyCredentialCreationOptions.getExtensions();
    }

}
