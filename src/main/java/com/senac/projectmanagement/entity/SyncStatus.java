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
