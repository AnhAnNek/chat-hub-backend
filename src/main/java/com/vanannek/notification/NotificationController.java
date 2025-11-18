package com.vanannek.notification;

import com.vanannek.notification.service.NotificationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notifications")
@Tag(name = "Notification")
public class NotificationController {

    private static final Logger log = LogManager.getLogger(NotificationController.class);

    @Autowired private NotificationService notificationService;

    @Operation(
            summary = "Add a notification",
            description = "Add a notification by providing the notification details.",
            responses = {
                    @ApiResponse(
                            description = "Created",
                            responseCode = "201"
                    ),
                    @ApiResponse(
                            description = "Unauthorized / Invalid Token",
                            responseCode = "401"
                    )
            }
    )
    @PostMapping("/add")
    public ResponseEntity<String> add(@RequestBody NotificationDTO notificationDTO) {
        notificationService.add(notificationDTO);
        log.info(NotificationUtils.NOTIFICATION_ADDED_SUCCESSFUL);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(NotificationUtils.NOTIFICATION_ADDED_SUCCESSFUL);
    }

    @Operation(
            summary = "Mark a notification as read",
            description = "Mark a notification as read by providing the notification id.",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "Unauthorized / Invalid Token",
                            responseCode = "401"
                    )
            }
    )
    @PostMapping("/mark-read/{notificationId}")
    public ResponseEntity<String> markNotificationAsRead(@PathVariable Long notificationId) {
        notificationService.markNotificationAsRead(notificationId);
        log.info(NotificationUtils.MARK_AS_READ_SUCCESSFUL);
        return ResponseEntity.ok(NotificationUtils.MARK_AS_READ_SUCCESSFUL);
    }

    @Operation(
            summary = "Retrieve notifications",
            description = "Retrieve notifications by providing the username.",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "Unauthorized / Invalid Token",
                            responseCode = "401"
                    )
            }
    )
    @GetMapping("/get-notifications/{username}")
    public ResponseEntity<List<NotificationDTO>> getNotifications(@PathVariable String username) {
        List<NotificationDTO> notificationDTOs = notificationService.getNotifications(username);
        log.info(NotificationUtils.RETRIEVED_NOTIFICATIONS_FOR_USER, username, notificationDTOs.size());
        return ResponseEntity.ok(notificationDTOs);
    }
}
