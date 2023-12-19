package com.vanannek.service.conversationmember;

import com.vanannek.dto.ConversationMemberDTO;
import com.vanannek.entity.Conversation;
import com.vanannek.entity.ConversationMember;
import com.vanannek.entity.User;
import com.vanannek.exception.ConversationMemberNotFoundException;
import com.vanannek.exception.ConversationNotFoundException;
import com.vanannek.exception.UserNotFoundException;
import com.vanannek.mapper.ConversationMemberMapper;
import com.vanannek.repos.ConversationMemberRepos;
import com.vanannek.repos.ConversationRepos;
import com.vanannek.repos.UserRepos;
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
