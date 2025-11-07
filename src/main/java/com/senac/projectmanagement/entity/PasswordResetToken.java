<<<<<<< HEAD
package com.senac.projectmanagement.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "password_reset_token")
public class PasswordResetToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "password_reset_token_id")
    private Long passwordResetTokenId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "password_reset_token_token", nullable = false, length = 255)
    private String passwordResetTokenToken;

    @Column(name = "password_reset_token_expires_at", nullable = false)
    private LocalDateTime passwordResetTokenExpiresAt;

    @Column(name = "password_reset_token_used")
    private Boolean passwordResetTokenUsed = false;

    @Column(name = "password_reset_token_created_at")
    private LocalDateTime passwordResetTokenCreatedAt;

    // Constructors, getters, setters

    public PasswordResetToken() {}

    public PasswordResetToken(User user, String passwordResetTokenToken, LocalDateTime passwordResetTokenExpiresAt) {
        this.user = user;
        this.passwordResetTokenToken = passwordResetTokenToken;
        this.passwordResetTokenExpiresAt = passwordResetTokenExpiresAt;
    }

    public Long getPasswordResetTokenId() {
        return passwordResetTokenId;
    }

    public void setPasswordResetTokenId(Long passwordResetTokenId) {
        this.passwordResetTokenId = passwordResetTokenId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getPasswordResetTokenToken() {
        return passwordResetTokenToken;
    }

    public void setPasswordResetTokenToken(String passwordResetTokenToken) {
        this.passwordResetTokenToken = passwordResetTokenToken;
    }

    public LocalDateTime getPasswordResetTokenExpiresAt() {
        return passwordResetTokenExpiresAt;
    }

    public void setPasswordResetTokenExpiresAt(LocalDateTime passwordResetTokenExpiresAt) {
        this.passwordResetTokenExpiresAt = passwordResetTokenExpiresAt;
    }

    public Boolean getPasswordResetTokenUsed() {
        return passwordResetTokenUsed;
    }

    public void setPasswordResetTokenUsed(Boolean passwordResetTokenUsed) {
        this.passwordResetTokenUsed = passwordResetTokenUsed;
    }

    public LocalDateTime getPasswordResetTokenCreatedAt() {
        return passwordResetTokenCreatedAt;
    }

    public void setPasswordResetTokenCreatedAt(LocalDateTime passwordResetTokenCreatedAt) {
        this.passwordResetTokenCreatedAt = passwordResetTokenCreatedAt;
    }

    @PrePersist
    protected void onCreate() {
        passwordResetTokenCreatedAt = LocalDateTime.now();
    }
}
=======
package com.senac.projectmanagement.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "Password_Reset_Tokens")
public class PasswordResetToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "password_reset_tokens_id")
    private Long passwordResetTokensId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "users_id", nullable = false)
    private User user;

    @Column(name = "token", nullable = false, length = 255)
    private String token;

    @Column(name = "expires_at", nullable = false)
    private LocalDateTime expiresAt;

    @Column(name = "used")
    private Boolean used = false;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    // Constructors, getters, setters

    public PasswordResetToken() {}

    public PasswordResetToken(User user, String token, LocalDateTime expiresAt) {
        this.user = user;
        this.token = token;
        this.expiresAt = expiresAt;
    }

    public Long getPasswordResetTokensId() {
        return passwordResetTokensId;
    }

    public void setPasswordResetTokensId(Long passwordResetTokensId) {
        this.passwordResetTokensId = passwordResetTokensId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}
>>>>>>> a93ef3251f7e8d9785c06f73e8935488a15f2178
