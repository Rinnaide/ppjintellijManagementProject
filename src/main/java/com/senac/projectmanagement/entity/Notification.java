package com.senac.projectmanagement.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "notification")
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notification_id")
    private Long notificationId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "notification_title", nullable = false, length = 255)
    private String notificationTitle;

    @Column(name = "notification_message", nullable = false, columnDefinition = "TEXT")
    private String notificationMessage;

    @Enumerated(EnumType.STRING)
    @Column(name = "notification_type")
    private NotificationType notificationType = NotificationType.INFO;

    @Column(name = "notification_is_read")
    private Boolean notificationIsRead = false;

    @Column(name = "notification_scheduled_at", nullable = false)
    private LocalDateTime notificationScheduledAt;

    @Column(name = "notification_sent_at")
    private LocalDateTime notificationSentAt;

    @Column(name = "notification_created_at")
    private LocalDateTime notificationCreatedAt;

    // Constructors, getters, setters

    public Notification() {}

    public Notification(User user, String notificationTitle, String notificationMessage, LocalDateTime notificationScheduledAt) {
        this.user = user;
        this.notificationTitle = notificationTitle;
        this.notificationMessage = notificationMessage;
        this.notificationScheduledAt = notificationScheduledAt;
    }

    public Long getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(Long notificationId) {
        this.notificationId = notificationId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getNotificationTitle() {
        return notificationTitle;
    }

    public void setNotificationTitle(String notificationTitle) {
        this.notificationTitle = notificationTitle;
    }

    public String getNotificationMessage() {
        return notificationMessage;
    }

    public void setNotificationMessage(String notificationMessage) {
        this.notificationMessage = notificationMessage;
    }

    public NotificationType getNotificationType() {
        return notificationType;
    }

    public void setNotificationType(NotificationType notificationType) {
        this.notificationType = notificationType;
    }

    public Boolean getNotificationIsRead() {
        return notificationIsRead;
    }

    public void setNotificationIsRead(Boolean notificationIsRead) {
        this.notificationIsRead = notificationIsRead;
    }

    public LocalDateTime getNotificationScheduledAt() {
        return notificationScheduledAt;
    }

    public void setNotificationScheduledAt(LocalDateTime notificationScheduledAt) {
        this.notificationScheduledAt = notificationScheduledAt;
    }

    public LocalDateTime getNotificationSentAt() {
        return notificationSentAt;
    }

    public void setNotificationSentAt(LocalDateTime notificationSentAt) {
        this.notificationSentAt = notificationSentAt;
    }

    public LocalDateTime getNotificationCreatedAt() {
        return notificationCreatedAt;
    }

    public void setNotificationCreatedAt(LocalDateTime notificationCreatedAt) {
        this.notificationCreatedAt = notificationCreatedAt;
    }

    @PrePersist
    protected void onCreate() {
        notificationCreatedAt = LocalDateTime.now();
    }
}