package com.vanannek.notification.service;

import com.vanannek.notification.Notification;
import com.vanannek.notification.NotificationDTO;

import java.util.List;

public interface NotificationService {
    NotificationDTO add(NotificationDTO notificationDTO);
    void markNotificationAsRead(Long notificationId);
    List<NotificationDTO> getNotifications(String username);
    void deleteAll();
    Notification getNotificationById(Long notificationId);
}
