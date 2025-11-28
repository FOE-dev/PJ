package vti.dtn.auth_service.dto.response;

public class LoginResponse {

    private int status;
    private String message;
    private Long userId;
    private String accessToken;
    private String refreshToken;

    public LoginResponse() {}

    public LoginResponse(int status, String message, Long userId, String accessToken, String refreshToken) {
        this.status = status;
        this.message = message;
        this.userId = userId;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
