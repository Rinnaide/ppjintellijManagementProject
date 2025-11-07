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