package com.vanannek.notification;

import com.vanannek.notification.service.NotificationService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    private static final Logger log = LogManager.getLogger(NotificationController.class);

    @Autowired private NotificationService notificationService;

    @PostMapping("/add")
    public ResponseEntity<String> add(@RequestBody NotificationDTO notificationDTO) {
        notificationService.add(notificationDTO);
        log.info(NotificationUtils.NOTIFICATION_ADDED_SUCCESSFUL);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(NotificationUtils.NOTIFICATION_ADDED_SUCCESSFUL);
    }

    @PostMapping("/mark-read/{notificationId}")
    public ResponseEntity<String> markNotificationAsRead(@PathVariable Long notificationId) {
        notificationService.markNotificationAsRead(notificationId);
        log.info(NotificationUtils.MARK_AS_READ_SUCCESSFUL);
        return ResponseEntity.ok(NotificationUtils.MARK_AS_READ_SUCCESSFUL);
    }

    @GetMapping("/get-notifications/{username}")
    public ResponseEntity<List<NotificationDTO>> getNotifications(@PathVariable String username) {
        List<NotificationDTO> notificationDTOs = notificationService.getNotifications(username);
        log.info(NotificationUtils.RETRIEVED_NOTIFICATIONS_FOR_USER, username, notificationDTOs.size());
        return ResponseEntity.ok(notificationDTOs);
    }
}
