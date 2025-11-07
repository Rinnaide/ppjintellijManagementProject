package com.senac.projectmanagement.dto;

import java.time.LocalDateTime;

public class PasswordResetTokenResponseDTO {

    private Long passwordResetTokenId;
    private Long userId;
    private String token;
    private LocalDateTime expiresAt;
    private Boolean used;
    private LocalDateTime createdAt;

    // Constructors, getters, setters

    public PasswordResetTokenResponseDTO() {}

    public PasswordResetTokenResponseDTO(Long passwordResetTokenId, Long userId, String token, LocalDateTime expiresAt, Boolean used, LocalDateTime createdAt) {
        this.passwordResetTokenId = passwordResetTokenId;
        this.userId = userId;
        this.token = token;
        this.expiresAt = expiresAt;
        this.used = used;
        this.createdAt = createdAt;
    }

    public Long getPasswordResetTokenId() {
        return passwordResetTokenId;
    }

    public void setPasswordResetTokenId(Long passwordResetTokenId) {
        this.passwordResetTokenId = passwordResetTokenId;
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

    public Boolean getUsed() {
        return used;
    }

    public void setUsed(Boolean used) {
        this.used = used;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
