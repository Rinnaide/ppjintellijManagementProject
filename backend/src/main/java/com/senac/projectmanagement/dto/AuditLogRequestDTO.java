package com.senac.projectmanagement.dto;

import com.senac.projectmanagement.entity.AuditLogAction;

public class AuditLogRequestDTO {

    private Long userId;
    private String tableName;
    private Integer recordId;
    private AuditLogAction action;
    private String oldValues;
    private String newValues;
    private String ipAddress;
    private String userAgent;

    // Constructors, getters, setters

    public AuditLogRequestDTO() {}

    public AuditLogRequestDTO(Long userId, String tableName, Integer recordId, AuditLogAction action) {
        this.userId = userId;
        this.tableName = tableName;
        this.recordId = recordId;
        this.action = action;
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

    public Integer getRecordId() {
        return recordId;
    }

    public void setRecordId(Integer recordId) {
        this.recordId = recordId;
    }

    public AuditLogAction getAction() {
        return action;
    }

    public void setAction(AuditLogAction action) {
        this.action = action;
    }

    public String getOldValues() {
        return oldValues;
    }

    public void setOldValues(String oldValues) {
        this.oldValues = oldValues;
    }

    public String getNewValues() {
        return newValues;
    }

    public void setNewValues(String newValues) {
        this.newValues = newValues;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }
}
