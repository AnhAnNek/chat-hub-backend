package com.vanannek.service.notificaiton;

import com.vanannek.dto.NotificationDTO;
import com.vanannek.entity.Notification;
import com.vanannek.entity.User;
import com.vanannek.exception.NotificationNotFoundException;
import com.vanannek.exception.UserNotFoundException;
import com.vanannek.mapper.NotificationMapper;
import com.vanannek.repos.NotificationRepos;
import com.vanannek.repos.UserRepos;
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
