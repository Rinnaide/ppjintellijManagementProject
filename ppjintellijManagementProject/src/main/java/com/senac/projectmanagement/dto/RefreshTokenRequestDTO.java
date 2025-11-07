package com.senac.projectmanagement.dto;

import java.time.LocalDateTime;

public class RefreshTokenRequestDTO {

    private Long userId;
    private String token;
    private LocalDateTime expiresAt;

    // Constructors, getters, setters

    public RefreshTokenRequestDTO() {}

    public RefreshTokenRequestDTO(Long userId, String token, LocalDateTime expiresAt) {
        this.userId = userId;
        this.token = token;
        this.expiresAt = expiresAt;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public LocalDateTime getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(LocalDateTime expiresAt) {
        this.expiresAt = expiresAt;
    }
}
