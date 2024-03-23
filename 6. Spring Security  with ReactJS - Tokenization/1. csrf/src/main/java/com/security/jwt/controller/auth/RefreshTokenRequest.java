package com.security.jwt.controller.auth;

public class RefreshTokenRequest {
    private String token;

    public RefreshTokenRequest(String token) {
        this.token = token;
    }

    // Getter
    public String getToken() {
        return token;
    }

    // Setter (optional)
    public void setToken(String token) {
        this.token = token;
    }
}
