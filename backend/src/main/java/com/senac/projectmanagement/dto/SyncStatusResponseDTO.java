package com.senac.projectmanagement.dto;

import java.time.LocalDateTime;

public class SyncStatusResponseDTO {

    private Long syncStatusId;
    private Long userId;
    private String tableName;
    private LocalDateTime lastSyncAt;
    private String status;
    private String errorMessage;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // Constructors, getters, setters

    public SyncStatusResponseDTO() {}

    public SyncStatusResponseDTO(Long syncStatusId, Long userId, String tableName, LocalDateTime lastSyncAt, String status, String errorMessage, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.syncStatusId = syncStatusId;
        this.userId = userId;
        this.tableName = tableName;
        this.lastSyncAt = lastSyncAt;
        this.status = status;
        this.errorMessage = errorMessage;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Long getSyncStatusId() {
        return syncStatusId;
    }

    public void setSyncStatusId(Long syncStatusId) {
        this.syncStatusId = syncStatusId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public LocalDateTime getLastSyncAt() {
        return lastSyncAt;
    }

    public void setLastSyncAt(LocalDateTime lastSyncAt) {
        this.lastSyncAt = lastSyncAt;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
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
}
