package com.vanannek.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@AllArgsConstructor
@Data
public class NotificationDTO {
    private Long id;
    private String message;
    private String username;
    private boolean read;
    private LocalDateTime createdAt;

    public NotificationDTO() {
        read = false;
        createdAt = LocalDateTime.now();
    }

    public NotificationDTO(String message, String username) {
        this();
        this.message = message;
        this.username = username;
    }
}
