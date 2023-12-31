package com.vanannek.chat.message.service;

import com.vanannek.chat.message.ChatMessageDTO;

import java.util.List;

public interface MessageService {
    ChatMessageDTO add(ChatMessageDTO cmDTO);
    List<ChatMessageDTO> addAll(List<ChatMessageDTO> cmDTOs);
    List<ChatMessageDTO> getMessages(String conversationId);
}
