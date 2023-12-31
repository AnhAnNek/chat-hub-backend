package com.vanannek.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class NotificationDTO {
    private Long id;
    private String message;
    private String username;
    private boolean read;
    private LocalDateTime createdAt;
}
