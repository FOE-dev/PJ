package vti.dtn.auth_service.dto.response;

public class VerifyTokenResponse {

    private Integer status;
    private String message;
    private String xUserToken;

    public VerifyTokenResponse() {}

    public VerifyTokenResponse(Integer status, String message, String xUserToken) {
        this.status = status;
        this.message = message;
        this.xUserToken = xUserToken;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getXUserToken() {
        return xUserToken;
    }

    public void setXUserToken(String xUserToken) {
        this.xUserToken = xUserToken;
    }
}
