package com.vanannek.service.message;

import com.vanannek.dto.ChatMessageDTO;

import java.util.List;

public interface MessageService {
    ChatMessageDTO add(ChatMessageDTO cmDTO);
    List<ChatMessageDTO> addAll(List<ChatMessageDTO> cmDTOs);
    List<ChatMessageDTO> getMessages(String conversationId);
}
