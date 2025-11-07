<<<<<<< HEAD
package com.senac.projectmanagement.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "sync_status")
public class SyncStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sync_status_id")
    private Long syncStatusId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "sync_status_table_name", nullable = false, length = 50)
    private String syncStatusTableName;

    @Column(name = "sync_status_last_sync_at")
    private LocalDateTime syncStatusLastSyncAt;

    @Column(name = "sync_status_status", nullable = false, length = 20)
    private String syncStatusStatus = "pending";

    @Column(name = "sync_status_error_message", columnDefinition = "TEXT")
    private String syncStatusErrorMessage;

    @Column(name = "sync_status_created_at")
    private LocalDateTime syncStatusCreatedAt;

    @Column(name = "sync_status_updated_at")
    private LocalDateTime syncStatusUpdatedAt;

    // Constructors, getters, setters

    public SyncStatus() {}

    public SyncStatus(User user, String syncStatusTableName) {
        this.user = user;
        this.syncStatusTableName = syncStatusTableName;
    }

    public Long getSyncStatusId() {
        return syncStatusId;
    }

    public void setSyncStatusId(Long syncStatusId) {
        this.syncStatusId = syncStatusId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getSyncStatusTableName() {
        return syncStatusTableName;
    }

    public void setSyncStatusTableName(String syncStatusTableName) {
        this.syncStatusTableName = syncStatusTableName;
    }

    public LocalDateTime getSyncStatusLastSyncAt() {
        return syncStatusLastSyncAt;
    }

    public void setSyncStatusLastSyncAt(LocalDateTime syncStatusLastSyncAt) {
        this.syncStatusLastSyncAt = syncStatusLastSyncAt;
    }

    public String getSyncStatusStatus() {
        return syncStatusStatus;
    }

    public void setSyncStatusStatus(String syncStatusStatus) {
        this.syncStatusStatus = syncStatusStatus;
    }

    public String getSyncStatusErrorMessage() {
        return syncStatusErrorMessage;
    }

    public void setSyncStatusErrorMessage(String syncStatusErrorMessage) {
        this.syncStatusErrorMessage = syncStatusErrorMessage;
    }

    public LocalDateTime getSyncStatusCreatedAt() {
        return syncStatusCreatedAt;
    }

    public void setSyncStatusCreatedAt(LocalDateTime syncStatusCreatedAt) {
        this.syncStatusCreatedAt = syncStatusCreatedAt;
    }

    public LocalDateTime getSyncStatusUpdatedAt() {
        return syncStatusUpdatedAt;
    }

    public void setSyncStatusUpdatedAt(LocalDateTime syncStatusUpdatedAt) {
        this.syncStatusUpdatedAt = syncStatusUpdatedAt;
    }

    @PrePersist
    protected void onCreate() {
        syncStatusCreatedAt = LocalDateTime.now();
        syncStatusUpdatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        syncStatusUpdatedAt = LocalDateTime.now();
    }
}
=======
package com.senac.projectmanagement.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "Sync_Status")
public class SyncStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sync_status_id")
    private Long syncStatusId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "users_id", nullable = false)
    private User user;

    @Column(name = "last_sync_at", nullable = false)
    private LocalDateTime lastSyncAt;

    @Column(name = "device_info", length = 255)
    private String deviceInfo;

    @Column(name = "sync_token", length = 255)
    private String syncToken;

    @Column(name = "pending_changes")
    private Integer pendingChanges = 0;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    // Constructors, getters, setters

    public SyncStatus() {}

    public SyncStatus(User user, LocalDateTime lastSyncAt) {
        this.user = user;
        this.lastSyncAt = lastSyncAt;
    }

    public Long getSyncStatusId() {
        return syncStatusId;
    }

    public void setSyncStatusId(Long syncStatusId) {
        this.syncStatusId = syncStatusId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDateTime getLastSyncAt() {
        return lastSyncAt;
    }

    public void setLastSyncAt(LocalDateTime lastSyncAt) {
        this.lastSyncAt = lastSyncAt;
    }

    public String getDeviceInfo() {
        return deviceInfo;
    }

    public void setDeviceInfo(String deviceInfo) {
        this.deviceInfo = deviceInfo;
    }

    public String getSyncToken() {
        return syncToken;
    }

    public void setSyncToken(String syncToken) {
        this.syncToken = syncToken;
    }

    public Integer getPendingChanges() {
        return pendingChanges;
    }

    public void setPendingChanges(Integer pendingChanges) {
        this.pendingChanges = pendingChanges;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
>>>>>>> a93ef3251f7e8d9785c06f73e8935488a15f2178
