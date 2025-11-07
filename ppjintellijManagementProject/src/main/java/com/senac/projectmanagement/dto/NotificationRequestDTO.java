package com.senac.projectmanagement.dto;

import com.senac.projectmanagement.entity.NotificationType;
import java.time.LocalDateTime;

public class NotificationRequestDTO {

    private Long userId;
    private String title;
    private String message;
    private NotificationType notificationType;
    private LocalDateTime scheduledAt;

    // Constructors, getters, setters

    public NotificationRequestDTO() {}

    public NotificationRequestDTO(Long userId, String title, String message, NotificationType notificationType, LocalDateTime scheduledAt) {
        this.userId = userId;
        this.title = title;
        this.message = message;
        this.notificationType = notificationType;
        this.scheduledAt = scheduledAt;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public NotificationType getNotificationType() {
        return notificationType;
    }

    public void setNotificationType(NotificationType notificationType) {
        this.notificationType = notificationType;
    }

    public LocalDateTime getScheduledAt() {
        return scheduledAt;
    }

    public void setScheduledAt(LocalDateTime scheduledAt) {
        this.scheduledAt = scheduledAt;
    }
}
