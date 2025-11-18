package com.vanannek.notification.service;

import com.vanannek.notification.*;
import com.vanannek.user.User;
import com.vanannek.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final NotificationMapper nMapper = NotificationMapper.INSTANCE;

    private final NotificationRepos notificationRepos;
    private final UserService userService;

    @Override
    public NotificationDTO add(NotificationDTO notificationDTO) {
        Notification notification = nMapper.toEntity(notificationDTO);

        String username = notificationDTO.getUsername() != null
                ? notificationDTO.getUsername() : "";
        User user = userService.getUserByUsername(username);
        notification.setUser(user);

        Notification saved = notificationRepos.save(notification);
        return nMapper.toDTO(saved);
    }

    @Override
    public void markNotificationAsRead(Long notificationId) {
        Notification notification = getNotificationById(notificationId);
        notification.setRead(true);

        notificationRepos.save(notification);
    }

    @Override
    public List<NotificationDTO> getNotifications(String username) {
        userService.getUserByUsername(username);

        List<Notification> notifications = notificationRepos.getNotifications(username);
        return nMapper.toDTOs(notifications);
    }

    @Override
    public void deleteAll() {
        notificationRepos.deleteAll();
    }

    @Override
    public Notification getNotificationById(Long notificationId) {
        String errorMsg = String.format("Could not find any notifications with id=%s", notificationId);
        return notificationRepos.findById(notificationId)
                .orElseThrow(() -> new NotificationNotFoundException(errorMsg));
    }
}
