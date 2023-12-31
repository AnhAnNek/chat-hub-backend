package com.vanannek.restcontroller;

import com.vanannek.dto.NotificationDTO;
import com.vanannek.exception.NotificationNotFoundException;
import com.vanannek.exception.UserNotFoundException;
import com.vanannek.service.notificaiton.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    @Autowired private NotificationService notificationService;

    @PostMapping("/add")
    public ResponseEntity<String> add(@RequestBody NotificationDTO notificationDTO) {
        try {
            notificationService.add(notificationDTO);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body("Add notification successfully");
        } catch (UserNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to add notification");
        }
    }

    @PostMapping("/mark-read/{notificationId}")
    public ResponseEntity<String> markNotificationAsRead(@PathVariable Long notificationId) {
        try {
            notificationService.markNotificationAsRead(notificationId);
            return ResponseEntity.ok("Notification marked as read successfully");
        } catch (NotificationNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to mark notification as read");
        }
    }

    @GetMapping("/get-notifications/{username}")
    public List<NotificationDTO> getNotifications(@PathVariable String username) {
        return notificationService.getNotifications(username);
    }
}
