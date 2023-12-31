package com.vanannek.chat.member.service;

import com.vanannek.chat.member.ConversationMemberDTO;
import com.vanannek.chat.conversation.Conversation;
import com.vanannek.chat.member.ConversationMember;
import com.vanannek.user.User;
import com.vanannek.chat.member.ConversationMemberNotFoundException;
import com.vanannek.chat.conversation.ConversationNotFoundException;
import com.vanannek.user.UserNotFoundException;
import com.vanannek.chat.member.ConversationMemberMapper;
import com.vanannek.chat.member.ConversationMemberRepos;
import com.vanannek.chat.conversation.ConversationRepos;
import com.vanannek.user.UserRepos;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ConversationMemberServiceImpl implements ConversationMemberService {

    @Autowired private ConversationRepos conversationRepos;
    @Autowired private ConversationMemberRepos memberRepos;
    @Autowired private UserRepos userRepos;
    private final ConversationMemberMapper memberMapper = ConversationMemberMapper.INSTANCE;

    @Override
    public ConversationMemberDTO add(ConversationMemberDTO conversationMemberDTO) {
        ConversationMember conversationMember = memberMapper.toEntity(conversationMemberDTO);

        String memberUsername = conversationMemberDTO.getMemberUsername();;
        User member = userRepos
                .findById(memberUsername)
                .orElseThrow(() -> new UserNotFoundException("Could not find any user with username=" + memberUsername));
        conversationMember.setMember(member);

        String conversationId = conversationMemberDTO.getConversationId();
        Conversation conversation = conversationRepos.findById(conversationId)
                .orElseThrow(() -> new ConversationNotFoundException("Could not find any conversation with id=" + conversationId));
        conversationMember.setConversation(conversation);

        ConversationMember saved = memberRepos.save(conversationMember);
        return memberMapper.toDTO(saved);
    }

    @Override
    @Transactional
    public ConversationMemberDTO deleteByUsername(String conversationId, String username) {
        ConversationMember deletedMember = memberRepos.deleteByUsername(conversationId, username);

        if (deletedMember != null) {
            return memberMapper.toDTO(deletedMember);
        } else {
            throw new ConversationMemberNotFoundException("Could not find any conversation member with username=" + username);
        }
    }
}
