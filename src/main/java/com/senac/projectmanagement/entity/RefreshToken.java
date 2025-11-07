package com.senac.projectmanagement.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "refresh_token")
public class RefreshToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "refresh_token_id")
    private Long refreshTokenId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "refresh_token_token", nullable = false, length = 255)
    private String refreshTokenToken;

    @Column(name = "refresh_token_expires_at", nullable = false)
    private LocalDateTime refreshTokenExpiresAt;

    @Column(name = "refresh_token_revoked")
    private Boolean refreshTokenRevoked = false;

    @Column(name = "refresh_token_created_at")
    private LocalDateTime refreshTokenCreatedAt;

    // Constructors, getters, setters

    public RefreshToken() {}

    public RefreshToken(User user, String refreshTokenToken, LocalDateTime refreshTokenExpiresAt) {
        this.user = user;
        this.refreshTokenToken = refreshTokenToken;
        this.refreshTokenExpiresAt = refreshTokenExpiresAt;
    }

    public Long getRefreshTokenId() {
        return refreshTokenId;
    }

    public void setRefreshTokenId(Long refreshTokenId) {
        this.refreshTokenId = refreshTokenId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getRefreshTokenToken() {
        return refreshTokenToken;
    }

    public void setRefreshTokenToken(String refreshTokenToken) {
        this.refreshTokenToken = refreshTokenToken;
    }

    public LocalDateTime getRefreshTokenExpiresAt() {
        return refreshTokenExpiresAt;
    }

    public void setRefreshTokenExpiresAt(LocalDateTime refreshTokenExpiresAt) {
        this.refreshTokenExpiresAt = refreshTokenExpiresAt;
    }

    public Boolean getRefreshTokenRevoked() {
        return refreshTokenRevoked;
    }

    public void setRefreshTokenRevoked(Boolean refreshTokenRevoked) {
        this.refreshTokenRevoked = refreshTokenRevoked;
    }

    public LocalDateTime getRefreshTokenCreatedAt() {
        return refreshTokenCreatedAt;
    }

    public void setRefreshTokenCreatedAt(LocalDateTime refreshTokenCreatedAt) {
        this.refreshTokenCreatedAt = refreshTokenCreatedAt;
    }

    @PrePersist
    protected void onCreate() {
        refreshTokenCreatedAt = LocalDateTime.now();
    }
}
