<<<<<<< HEAD
package com.senac.projectmanagement.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "audit_log")
public class AuditLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "audit_log_id")
    private Long auditLogId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "audit_table_name", nullable = false, length = 50)
    private String auditTableName;

    @Column(name = "audit_record_id", nullable = false)
    private Integer auditRecordId;

    @Enumerated(EnumType.STRING)
    @Column(name = "audit_action")
    private AuditLogAction auditAction;

    @Column(name = "audit_ip_address", length = 45, nullable = false)
    private String auditIpAddress;

    @Column(name = "audit_user_agent", columnDefinition = "TEXT", nullable = false)
    private String auditUserAgent;

    @Column(name = "audit_created_at")
    private LocalDateTime auditCreatedAt;

    // Constructors, getters, setters

    public AuditLog() {}

    public AuditLog(User user, String auditTableName, Integer auditRecordId, AuditLogAction auditAction, String auditIpAddress, String auditUserAgent) {
        this.user = user;
        this.auditTableName = auditTableName;
        this.auditRecordId = auditRecordId;
        this.auditAction = auditAction;
        this.auditIpAddress = auditIpAddress;
        this.auditUserAgent = auditUserAgent;
    }

    public Long getAuditLogId() {
        return auditLogId;
    }

    public void setAuditLogId(Long auditLogId) {
        this.auditLogId = auditLogId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getAuditTableName() {
        return auditTableName;
    }

    public void setAuditTableName(String auditTableName) {
        this.auditTableName = auditTableName;
    }

    public Integer getAuditRecordId() {
        return auditRecordId;
    }

    public void setAuditRecordId(Integer auditRecordId) {
        this.auditRecordId = auditRecordId;
    }

    public AuditLogAction getAuditAction() {
        return auditAction;
    }

    public void setAuditAction(AuditLogAction auditAction) {
        this.auditAction = auditAction;
    }

    public String getAuditIpAddress() {
        return auditIpAddress;
    }

    public void setAuditIpAddress(String auditIpAddress) {
        this.auditIpAddress = auditIpAddress;
    }

    public String getAuditUserAgent() {
        return auditUserAgent;
    }

    public void setAuditUserAgent(String auditUserAgent) {
        this.auditUserAgent = auditUserAgent;
    }

    public LocalDateTime getAuditCreatedAt() {
        return auditCreatedAt;
    }

    public void setAuditCreatedAt(LocalDateTime auditCreatedAt) {
        this.auditCreatedAt = auditCreatedAt;
    }

    @PrePersist
    protected void onCreate() {
        auditCreatedAt = LocalDateTime.now();
    }
}
=======
package com.senac.projectmanagement.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "Audit_Logs")
public class AuditLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "audit_logs_id")
    private Long auditLogsId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "users_id")
    private User user;

    @Column(name = "table_name", nullable = false, length = 50)
    private String tableName;

    @Column(name = "record_id", nullable = false)
    private Integer recordId;

    @Enumerated(EnumType.STRING)
    @Column(name = "action", nullable = false)
    private AuditLogAction action;

    @Column(name = "old_values", columnDefinition = "JSON")
    private String oldValues;

    @Column(name = "new_values", columnDefinition = "JSON")
    private String newValues;

    @Column(name = "ip_address", length = 45)
    private String ipAddress;

    @Column(name = "user_agent", columnDefinition = "TEXT")
    private String userAgent;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    // Constructors, getters, setters

    public AuditLog() {}

    public AuditLog(User user, String tableName, Integer recordId, AuditLogAction action) {
        this.user = user;
        this.tableName = tableName;
        this.recordId = recordId;
        this.action = action;
    }

    public Long getAuditLogsId() {
        return auditLogsId;
    }

    public void setAuditLogsId(Long auditLogsId) {
        this.auditLogsId = auditLogsId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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
