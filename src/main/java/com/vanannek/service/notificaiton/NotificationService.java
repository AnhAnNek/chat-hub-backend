package com.vanannek.service.notificaiton;

import com.vanannek.dto.NotificationDTO;

import java.util.List;

public interface NotificationService {
    NotificationDTO add(NotificationDTO notificationDTO);
    void markNotificationAsRead(Long notificationId);
    List<NotificationDTO> getNotifications(String username);
    void deleteAll();
}
