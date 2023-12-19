package com.vanannek.service.conversationmember;

import com.vanannek.dto.ConversationMemberDTO;

public interface ConversationMemberService {
    ConversationMemberDTO add(ConversationMemberDTO memberDTO);
    ConversationMemberDTO deleteByUsername(String conversationId, String username);
}
