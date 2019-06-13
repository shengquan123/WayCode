package demo.webauthn.testdata;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.yubico.webauthn.data.AttestationConveyancePreference;
import com.yubico.webauthn.data.AuthenticatorAttachment;
import com.yubico.webauthn.data.AuthenticatorSelectionCriteria;
import com.yubico.webauthn.data.UserVerificationRequirement;
import lombok.Data;
import lombok.ToString;

import javax.annotation.ManagedBean;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Registration Primary IDL
 * 接收注册1请求数据实体类
 *
 * @author:shengquan
 * @Date:2019/5/15 12:14
 */
@Data
@ToString
public class ServerPublicKeyCredentialCreationOptionsRequest {

    private String username;
    private String displayName;
    private AuthenticatorSelectionCriteria authenticatorSelection;
    private AttestationConveyancePreference attestation = AttestationConveyancePreference.NONE;


    public static ServerPublicKeyCredentialCreationOptionsRequest readFromJson(String json) throws Exception {
        ServerPublicKeyCredentialCreationOptionsRequest request = new ServerPublicKeyCredentialCreationOptionsRequest();
        Map<String ,Object>map = JSON.parseObject(json);
        request.setUsername((String) map.get("username"));
        request.setDisplayName((String)map.get("displayName"));
        request.setAttestation(AttestationConveyancePreference.fromJsonStr((String) map.get("attestation")));

        Map<String, Object> mapSub = new HashMap<>();
        try {
            mapSub = JSON.parseObject(JSON.toJSONString(map.get("authenticatorSelection")));
        } catch (Exception e) {
            AuthenticatorSelectionCriteria build = AuthenticatorSelectionCriteria.builder().build();
            request.setAuthenticatorSelection(build);
            return request;
        }
        
        Optional<AuthenticatorAttachment> authenticatorAttachment;
        try {
            authenticatorAttachment = Optional.ofNullable(AuthenticatorAttachment.fromJsonStr((String) mapSub.get(
                    "authenticatorAttachment")));
        } catch (Exception e) {
            authenticatorAttachment = Optional.empty();
        }

        Boolean residentKey;
        try {
            residentKey = (Boolean) mapSub.get("requireResidentKey");
        } catch (Exception e) {
            residentKey = false;
        }

        UserVerificationRequirement userVerificationRequirement;
        try {
            userVerificationRequirement = UserVerificationRequirement.fromJsonStr((String) mapSub.get(
                    "userVerification"));
        } catch (Exception e) {
            userVerificationRequirement = UserVerificationRequirement.PREFERRED; // 没有，设置默认值
        }

        request.setAuthenticatorSelection(
                AuthenticatorSelectionCriteria.builder()
                    .authenticatorAttachment(
                            authenticatorAttachment
                    ).requireResidentKey(residentKey)
                    .userVerification(userVerificationRequirement)
                    .build()
        );
        return request;
    }
}
