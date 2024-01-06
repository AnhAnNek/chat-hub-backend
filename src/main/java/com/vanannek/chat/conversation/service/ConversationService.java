package com.vanannek.chat.conversation.service;

import com.vanannek.chat.conversation.Conversation;
import com.vanannek.chat.conversation.ConversationDTO;

import java.util.List;

public interface ConversationService {
    ConversationDTO save(ConversationDTO conversationDTO);
    ConversationDTO saveConversationWithMessagesAndMembers(ConversationDTO conversationDTO);
    void deleteAll();
    void deleteById(String id);
    ConversationDTO getById(String conversationId);
    List<ConversationDTO> getConversationsByUsername(String username);
    Conversation getConversationById(String conversationId);
}
