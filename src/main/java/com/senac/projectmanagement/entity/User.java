package com.senac.projectmanagement.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;

@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "user_email", nullable = false, unique = true, length = 255)
    private String userEmail;

    @Column(name = "user_password_hash", nullable = false, length = 255)
    private String userPasswordHash;

    @Column(name = "user_first_name", nullable = false, length = 100)
    private String userFirstName;

    @Column(name = "user_last_name", nullable = false, length = 100)
    private String userLastName;

    @Column(name = "user_phone", length = 20)
    private String userPhone;

    @Column(name = "user_default_currency", length = 3)
    private String userDefaultCurrency = "BRL";

    @Column(name = "user_timezone", length = 50)
    private String userTimezone = "America/Sao_Paulo";

    @Column(name = "user_is_active")
    private Boolean userIsActive = true;

    @Column(name = "user_email_verified")
    private Boolean userEmailVerified = false;

    @Column(name = "user_created_at")
    private LocalDateTime userCreatedAt;

    @Column(name = "user_updated_at", nullable = false)
    private LocalDateTime userUpdatedAt;

    // Constructors, getters, setters

    public User() {}

    public User(String userEmail, String userPasswordHash, String userFirstName, String userLastName) {
        this.userEmail = userEmail;
        this.userPasswordHash = userPasswordHash;
        this.userFirstName = userFirstName;
        this.userLastName = userLastName;
    }

    // Getters and Setters

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserPasswordHash() {
        return userPasswordHash;
    }

    public void setUserPasswordHash(String userPasswordHash) {
        this.userPasswordHash = userPasswordHash;
    }

    public String getUserFirstName() {
        return userFirstName;
    }

    public void setUserFirstName(String userFirstName) {
        this.userFirstName = userFirstName;
    }

    public String getUserLastName() {
        return userLastName;
    }

    public void setUserLastName(String userLastName) {
        this.userLastName = userLastName;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getUserDefaultCurrency() {
        return userDefaultCurrency;
    }

    public void setUserDefaultCurrency(String userDefaultCurrency) {
        this.userDefaultCurrency = userDefaultCurrency;
    }

    public String getUserTimezone() {
        return userTimezone;
    }

    public void setUserTimezone(String userTimezone) {
        this.userTimezone = userTimezone;
    }

    public Boolean getUserIsActive() {
        return userIsActive;
    }

    public void setUserIsActive(Boolean userIsActive) {
        this.userIsActive = userIsActive;
    }

    public Boolean getUserEmailVerified() {
        return userEmailVerified;
    }

    public void setUserEmailVerified(Boolean userEmailVerified) {
        this.userEmailVerified = userEmailVerified;
    }

    public LocalDateTime getUserCreatedAt() {
        return userCreatedAt;
    }

    public void setUserCreatedAt(LocalDateTime userCreatedAt) {
        this.userCreatedAt = userCreatedAt;
    }

    public LocalDateTime getUserUpdatedAt() {
        return userUpdatedAt;
    }

    public void setUserUpdatedAt(LocalDateTime userUpdatedAt) {
        this.userUpdatedAt = userUpdatedAt;
    }

    @PrePersist
    protected void onCreate() {
        userCreatedAt = LocalDateTime.now();
        userUpdatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        userUpdatedAt = LocalDateTime.now();
    }
}
