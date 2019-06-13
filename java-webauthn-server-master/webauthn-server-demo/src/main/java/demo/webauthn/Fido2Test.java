package demo.webauthn;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yubico.internal.util.WebAuthnCodecs;
import com.yubico.webauthn.extension.appid.InvalidAppIdException;
import demo.webauthn.testdata.ServerAuthenticatorAttestationResponse;
import demo.webauthn.testdata.ServerPublicKeyCredentialCreationOptionsRequest;
import demo.webauthn.testdata.ServerResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.security.cert.CertificateException;

/**
 * @author:shengquan
 * @Date:2019/5/31 17:47
 */
@Path("/")
@Produces(MediaType.APPLICATION_JSON)
public class Fido2Test {

    private static final Logger logger = LoggerFactory.getLogger(Fido2Test.class);
    private static final String TAG = "FidoTest";

    /**
     * RP_NAME
     */
    private static final String RP_NAME = "WAY_FIDO2_SERVER";

    private final WebAuthnServer server;
    private final ObjectMapper jsonMapper = WebAuthnCodecs.json();

    public Fido2Test() throws InvalidAppIdException, CertificateException {
        this(new WebAuthnServer());
    }


    public Fido2Test(WebAuthnServer server) {
        this.server = server;
    }

    @Path("attestation/options")
    @POST
    @Consumes(MediaType.APPLICATION_JSON + ";charset=UTF-8")
    @Produces(MediaType.APPLICATION_JSON)
    public String startTestReg(/*ServerPublicKeyCredentialCreationOptionsRequest*/ String request) {
        logger.info(TAG + "/startTestReg-开始注册数据：" + request);

        try {
            ServerPublicKeyCredentialCreationOptionsRequest optionsRequest =
                    ServerPublicKeyCredentialCreationOptionsRequest.readFromJson(request);

            return server.startRegTest(optionsRequest);
        } catch (Exception e) {
            e.printStackTrace();
            return JSON.toJSONString(new ServerResponse("failed","开始注册数据异常"));
        }
    }

    @Path("attestation/result")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public String finishTestReg(String response) {
        logger.info(TAG + "/finishTestReg：注册认证器返回数据:{}", response);

        /*ServerAuthenticatorAttestationResponse optionsResponse =
                ServerAuthenticatorAttestationResponse.readFromJson(response);*/
        ServerAuthenticatorAttestationResponse serverAuthenticatorAttestationResponse = JSON.parseObject(response,
                ServerAuthenticatorAttestationResponse.class);
        String finishResponse = server.testFinishReg(serverAuthenticatorAttestationResponse);


        return finishResponse;
    }
}
