package demo.webauthn.testdata;

import lombok.Data;
import lombok.ToString;

import java.util.Map;

/**
 * Generally the same as AuthenticatorAttestationResponse from WebAuthn,
 * but uses base64url encoding for fields that were of type BufferSource.
 * <p>
 * dictionary ServerAuthenticatorAttestationResponse : ServerAuthenticatorResponse {
 * required DOMString      clientDataJSON;
 * required DOMString      attestationObject;
 * };
 *
 * @author:shengquan
 * @Date:2019/5/20 18:00
 */
@Data
@ToString
public class ServerAuthenticatorAttestationResponse {

    private String id;

    private String rawId;

    private Map<String,Object> response;

    private String type;
}
