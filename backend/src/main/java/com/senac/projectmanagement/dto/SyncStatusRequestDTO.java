package com.senac.projectmanagement.dto;

import java.time.LocalDateTime;

public class SyncStatusRequestDTO {

    private Long userId;
    private String tableName;
    private LocalDateTime lastSyncAt;
    private String status;
    private String errorMessage;

    // Constructors, getters, setters

    public SyncStatusRequestDTO() {}

    public SyncStatusRequestDTO(Long userId, String tableName, LocalDateTime lastSyncAt, String status, String errorMessage) {
        this.userId = userId;
        this.tableName = tableName;
        this.lastSyncAt = lastSyncAt;
        this.status = status;
        this.errorMessage = errorMessage;
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
}
