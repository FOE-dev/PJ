package vti.dtn.api_gateway.response;

public class VerifyTokenResponse {

    private Integer status;
    private String message;
    private String xUserToken;

    public Integer getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public String getXUserToken() {
        return xUserToken;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setXUserToken(String xUserToken) {
        this.xUserToken = xUserToken;
    }
}
