package com.senac.projectmanagement.repository;

import com.senac.projectmanagement.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {

    List<Notification> findByUser_UserIdOrderByNotificationScheduledAtDesc(Long userId);

    List<Notification> findByUser_UserIdAndNotificationIsReadFalseOrderByNotificationScheduledAtDesc(Long userId);
}
