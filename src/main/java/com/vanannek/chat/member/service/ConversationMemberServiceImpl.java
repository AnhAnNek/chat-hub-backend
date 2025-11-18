package com.vanannek.chat.member.service;

import com.vanannek.chat.conversation.service.ConversationService;
import com.vanannek.chat.member.ConversationMemberDTO;
import com.vanannek.chat.conversation.Conversation;
import com.vanannek.chat.member.ConversationMember;
import com.vanannek.user.User;
import com.vanannek.chat.member.ConversationMemberNotFoundException;
import com.vanannek.chat.member.ConversationMemberMapper;
import com.vanannek.chat.member.ConversationMemberRepos;
import com.vanannek.user.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ConversationMemberServiceImpl implements ConversationMemberService {

    private final ConversationMemberRepos memberRepos;
    private final ConversationService conversationService;
    private final UserService userService;
    private final ConversationMemberMapper memberMapper = ConversationMemberMapper.INSTANCE;

    @Override
    public ConversationMemberDTO add(ConversationMemberDTO conversationMemberDTO) {
        ConversationMember conversationMember = memberMapper.toEntity(conversationMemberDTO);

        String memberUsername = conversationMemberDTO.getMemberUsername();;
        User member = userService.getUserByUsername(memberUsername);
        conversationMember.setMember(member);

        String conversationId = conversationMemberDTO.getConversationId();
        Conversation conversation = conversationService.getConversationById(conversationId);
        conversationMember.setConversation(conversation);

        ConversationMember saved = memberRepos.save(conversationMember);
        return memberMapper.toDTO(saved);
    }

    @Override
    @Transactional
    public ConversationMemberDTO deleteByUsername(String conversationId, String username) {
        getMemberByConversationAndUsername(conversationId, username);

        ConversationMember deletedMember = memberRepos.deleteByUsername(conversationId, username);
        return memberMapper.toDTO(deletedMember);
    }

    @Override
    public ConversationMember getMemberByConversationAndUsername(String conversationId, String username) {
        String errorMsg = String.format("Could not find any conversation member with conversationId=%s, username=%s",
                conversationId, username);
        return memberRepos.findByConversationIdAndUsername(conversationId, username)
                .orElseThrow(() -> new ConversationMemberNotFoundException(errorMsg));
    }
}
