package com.vanannek.service.conversation;

import com.vanannek.dto.ConversationDTO;

import java.util.List;

public interface ConversationService {
    ConversationDTO save(ConversationDTO conversationDTO);
    ConversationDTO saveConversationWithMessagesAndMembers(ConversationDTO conversationDTO);
    void deleteAll();
    void deleteById(String id);
    ConversationDTO getById(String conversationId);
    List<ConversationDTO> getConversationsByUsername(String username);
}
