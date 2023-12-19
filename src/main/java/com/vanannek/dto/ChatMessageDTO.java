package com.vanannek.dto;

import com.vanannek.entity.ChatMessage;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class ChatMessageDTO {
    private Long id;
    private String content;
    private ChatMessage.EType type;
    private Timestamp sendingTime;
    private String senderUsername;
    private String conversationId;
}
