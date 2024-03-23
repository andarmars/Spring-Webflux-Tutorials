package com.security.jwt.controller.auth;

public class TokenResponse {
    private String token;

    public TokenResponse(String token) {
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

