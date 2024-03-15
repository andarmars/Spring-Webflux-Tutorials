package com.security.jwt.controller.user;


import java.util.UUID;

public class UserResponse {
    private UUID uuid;
    private String email;

    public UserResponse(UUID uuid, String email) {
        this.uuid = uuid;
        this.email = email;
    }

    // Getters and setters
    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}

