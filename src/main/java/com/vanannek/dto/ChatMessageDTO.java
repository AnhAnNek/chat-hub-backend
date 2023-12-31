package com.vanannek.dto;

import com.vanannek.entity.ChatMessage;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class ChatMessageDTO {
    private Long id;
    private String content;
    private ChatMessage.EType type;
    private LocalDateTime sendingTime;
    private String senderUsername;
    private String conversationId;
}
