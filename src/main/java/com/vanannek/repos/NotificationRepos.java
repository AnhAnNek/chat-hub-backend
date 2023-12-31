package com.vanannek.repos;

import com.vanannek.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface NotificationRepos extends JpaRepository<Notification, Long> {
    @Query(value = "SELECT n FROM Notification n " +
            "WHERE n.user.username = :username ORDER BY n.createdAt")
    List<Notification> getNotifications(@Param("username") String username);
}
