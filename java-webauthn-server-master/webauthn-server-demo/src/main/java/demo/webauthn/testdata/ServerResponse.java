package demo.webauthn.testdata;

import lombok.Data;
import lombok.ToString;

/**
 * @author:shengquan
 * @Date:2019/5/27 19:34
 */
@Data
@ToString
public class ServerResponse {

    private String status;
    private String errorMessage;

    public ServerResponse() {
    }

    public ServerResponse(String status, String errorMessage) {
        this.status = status;
        this.errorMessage = errorMessage;
    }
}
