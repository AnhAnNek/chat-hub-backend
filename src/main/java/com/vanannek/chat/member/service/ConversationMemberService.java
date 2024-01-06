package com.vanannek.chat.member.service;

import com.vanannek.chat.member.ConversationMember;
import com.vanannek.chat.member.ConversationMemberDTO;

public interface ConversationMemberService {
    ConversationMemberDTO add(ConversationMemberDTO memberDTO);
    ConversationMemberDTO deleteByUsername(String conversationId, String username);
    ConversationMember getMemberByConversationAndUsername(String conversationId, String username);
}
