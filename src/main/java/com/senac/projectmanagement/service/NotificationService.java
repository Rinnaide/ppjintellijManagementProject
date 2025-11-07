package com.senac.projectmanagement.service;

import com.senac.projectmanagement.dto.NotificationRequestDTO;
import com.senac.projectmanagement.dto.NotificationResponseDTO;
import com.senac.projectmanagement.entity.Notification;
import com.senac.projectmanagement.entity.NotificationType;
import com.senac.projectmanagement.entity.User;
import com.senac.projectmanagement.repository.NotificationRepository;
import com.senac.projectmanagement.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private UserRepository userRepository;

    public Notification createNotification(Long userId, String title, String message, NotificationType notificationType, LocalDateTime scheduledAt) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()) {
            Notification notification = new Notification(user.get(), title, message, scheduledAt);
            notification.setNotificationType(notificationType);
            return notificationRepository.save(notification);
        }
        throw new RuntimeException("Usuário não encontrado");
    }

    public List<Notification> getNotificationsByUser(Long userId) {
        return notificationRepository.findByUser_UserIdOrderByNotificationScheduledAtDesc(userId);
    }

    public List<Notification> getUnreadNotificationsByUser(Long userId) {
        return notificationRepository.findByUser_UserIdAndNotificationIsReadFalseOrderByNotificationScheduledAtDesc(userId);
    }

    public Optional<Notification> getNotificationById(Long id) {
        return notificationRepository.findById(id);
    }

    public Notification markAsRead(Long id) {
        Optional<Notification> optionalNotification = notificationRepository.findById(id);
        if (optionalNotification.isPresent()) {
            Notification notification = optionalNotification.get();
            notification.setNotificationIsRead(true);
            notification.setNotificationSentAt(LocalDateTime.now());
            return notificationRepository.save(notification);
        }
        throw new RuntimeException("Notificação não encontrada");
    }

    public void deleteNotification(Long id) {
        notificationRepository.deleteById(id);
    }

    public NotificationResponseDTO createNotification(NotificationRequestDTO notificationRequestDTO) {
        Optional<User> user = userRepository.findById(notificationRequestDTO.getUserId());
        if (user.isPresent()) {
            Notification notification = new Notification(user.get(), notificationRequestDTO.getTitle(), notificationRequestDTO.getMessage(), notificationRequestDTO.getScheduledAt());
            notification.setNotificationType(notificationRequestDTO.getNotificationType());
            Notification savedNotification = notificationRepository.save(notification);
            return convertToResponseDTO(savedNotification);
        }
        throw new RuntimeException("Usuário não encontrado");
    }

    public List<NotificationResponseDTO> getAllNotifications() {
        List<Notification> notifications = notificationRepository.findAll();
        return notifications.stream().map(this::convertToResponseDTO).collect(Collectors.toList());
    }

    public Optional<NotificationResponseDTO> getNotificationByIdDTO(Long id) {
        return notificationRepository.findById(id).map(this::convertToResponseDTO);
    }

    public List<NotificationResponseDTO> getNotificationsByUserDTO(Long userId) {
        List<Notification> notifications = notificationRepository.findByUser_UserIdOrderByNotificationScheduledAtDesc(userId);
        return notifications.stream().map(this::convertToResponseDTO).collect(Collectors.toList());
    }

    public List<NotificationResponseDTO> getUnreadNotificationsByUserDTO(Long userId) {
        List<Notification> notifications = notificationRepository.findByUser_UserIdAndNotificationIsReadFalseOrderByNotificationScheduledAtDesc(userId);
        return notifications.stream().map(this::convertToResponseDTO).collect(Collectors.toList());
    }

    public NotificationResponseDTO markAsReadDTO(Long id) {
        Optional<Notification> optionalNotification = notificationRepository.findById(id);
        if (optionalNotification.isPresent()) {
            Notification notification = optionalNotification.get();
            notification.setNotificationIsRead(true);
            notification.setNotificationSentAt(LocalDateTime.now());
            Notification savedNotification = notificationRepository.save(notification);
            return convertToResponseDTO(savedNotification);
        }
        throw new RuntimeException("Notificação não encontrada");
    }

    private NotificationResponseDTO convertToResponseDTO(Notification notification) {
        NotificationResponseDTO dto = new NotificationResponseDTO();
        dto.setNotificationId(notification.getNotificationId());
        dto.setUserId(notification.getUser().getUserId());
        dto.setTitle(notification.getNotificationTitle());
        dto.setMessage(notification.getNotificationMessage());
        dto.setNotificationType(notification.getNotificationType());
        dto.setIsRead(notification.getNotificationIsRead());
        dto.setScheduledAt(notification.getNotificationScheduledAt());
        dto.setSentAt(notification.getNotificationSentAt());
        dto.setCreatedAt(notification.getNotificationCreatedAt());
        return dto;
    }
}
