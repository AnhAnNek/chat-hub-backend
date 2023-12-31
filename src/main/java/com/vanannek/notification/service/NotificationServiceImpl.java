package com.vanannek.notification.service;

import com.vanannek.notification.NotificationDTO;
import com.vanannek.notification.Notification;
import com.vanannek.user.User;
import com.vanannek.notification.NotificationNotFoundException;
import com.vanannek.user.UserNotFoundException;
import com.vanannek.notification.NotificationMapper;
import com.vanannek.notification.NotificationRepos;
import com.vanannek.user.UserRepos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationServiceImpl implements NotificationService {

    @Autowired private NotificationRepos notificationRepos;
    @Autowired private UserRepos userRepos;
    private final NotificationMapper nMapper = NotificationMapper.INSTANCE;

    @Override
    public NotificationDTO add(NotificationDTO notificationDTO) {
        Notification notification = nMapper.toEntity(notificationDTO);

        String username = notificationDTO.getUsername();
        User user = userRepos.findById(username)
                        .orElseThrow(() -> new UserNotFoundException("Could not find any user with username=" + username));
        notification.setUser(user);

        Notification saved = notificationRepos.save(notification);
        return nMapper.toDTO(saved);
    }

    @Override
    public void markNotificationAsRead(Long notificationId) {
        Notification notification = notificationRepos
                .findById(notificationId)
                .orElseThrow(() -> new NotificationNotFoundException("Could not find any notifications with id=" + notificationId));
        notification.setRead(true);

        notificationRepos.save(notification);
    }

    @Override
    public List<NotificationDTO> getNotifications(String username) {
        List<Notification> notifications = notificationRepos.getNotifications(username);
        return nMapper.toDTOs(notifications);
    }

    @Override
    public void deleteAll() {
        notificationRepos.deleteAll();
    }
}
