<<<<<<< HEAD
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
=======
package com.senac.projectmanagement.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "Refresh_Tokens")
public class RefreshToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "refresh_tokens_id")
    private Long refreshTokensId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "users_id", nullable = false)
    private User user;

    @Column(name = "token_hash", nullable = false, length = 255)
    private String tokenHash;

    @Column(name = "expires_at", nullable = false)
    private LocalDateTime expiresAt;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "is_revoked")
    private Boolean isRevoked = false;

    // Constructors, getters, setters

    public RefreshToken() {}

    public RefreshToken(User user, String tokenHash, LocalDateTime expiresAt) {
        this.user = user;
        this.tokenHash = tokenHash;
        this.expiresAt = expiresAt;
    }

    public Long getRefreshTokensId() {
        return refreshTokensId;
    }

    public void setRefreshTokensId(Long refreshTokensId) {
        this.refreshTokensId = refreshTokensId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getTokenHash() {
        return tokenHash;
    }

    public void setTokenHash(String tokenHash) {
        this.tokenHash = tokenHash;
    }

    public LocalDateTime getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(LocalDateTime expiresAt) {
        this.expiresAt = expiresAt;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Boolean getIsRevoked() {
        return isRevoked;
    }

    public void setIsRevoked(Boolean isRevoked) {
        this.isRevoked = isRevoked;
    }

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}
>>>>>>> a93ef3251f7e8d9785c06f73e8935488a15f2178
