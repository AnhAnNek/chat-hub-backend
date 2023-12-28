package com.vanannek.service.conversation;

import com.vanannek.dto.ConversationDTO;
import com.vanannek.entity.Conversation;
import com.vanannek.entity.User;
import com.vanannek.exception.ConversationNotFoundException;
import com.vanannek.mapper.ConversationMapper;
import com.vanannek.mapper.ConversationMemberMapper;
import com.vanannek.mapper.MessageMapper;
import com.vanannek.repos.ChatMessageRepos;
import com.vanannek.repos.ConversationMemberRepos;
import com.vanannek.repos.ConversationRepos;
import com.vanannek.repos.UserRepos;
import com.vanannek.util.ConversationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConversationServiceImpl implements ConversationService {

    @Autowired private ConversationRepos conversationRepos;
    @Autowired private ChatMessageRepos messageRepos;
    @Autowired private ConversationMemberRepos memberRepos;
    @Autowired private UserRepos userRepos;
    private final ConversationMapper conversationMapper = ConversationMapper.INSTANCE;
    private final MessageMapper messageMapper = MessageMapper.INSTANCE;
    private final ConversationMemberMapper memberMapper = ConversationMemberMapper.INSTANCE;

    @Override
    public ConversationDTO save(ConversationDTO conversationDTO) {
        Conversation conversation = conversationMapper.toEntity(conversationDTO);
        Conversation saved = conversationRepos.save(conversation);
        return conversationMapper.toDTO(saved);
    }

    @Override
    public ConversationDTO saveConversationWithMessagesAndMembers(ConversationDTO conversationDTO) {
        Conversation conversation = conversationMapper.toEntity(conversationDTO);

        conversation.getChatMessages().forEach(chatMessage -> {
            if (chatMessage == null) {
                return;
            }

            String senderUsername = chatMessage.getSenderUsername();


            User sender = userRepos.findById(senderUsername)
                            .orElse(null);
            chatMessage.setSender(sender);
            chatMessage.setConversation(conversation);
        });

        conversation.getMembers().forEach(conversationMember -> {
            if (conversationMember == null) {
                return;
            }

            String memberUsername = conversationMember.getMemberUsername();
            User member = userRepos.findById(memberUsername)
                    .orElse(null);
            conversationMember.setMember(member);
            conversationMember.setConversation(conversation);
        });

        Conversation saved = conversationRepos.save(conversation);
        return conversationMapper.toDTO(saved);
    }

    @Override
    public void deleteAll() {
        conversationRepos.deleteAll();
    }

    @Override
    public void deleteById(String id) {
        conversationRepos.deleteById(id);
    }

    @Override
    public ConversationDTO getById(String conversationId) {
        Conversation conversation = conversationRepos.findById(conversationId)
                .orElseThrow(() -> new ConversationNotFoundException("Could not find any conversation"));
        return conversationMapper.toDTO(conversation);
    }

    @Override
    public List<ConversationDTO> getConversationsByUsername(String username) {
        List<String> conversationIds = memberRepos.getConversationIdsByUsername(username);
        List<Conversation> conversations = conversationRepos.findAllById(conversationIds);
        conversations.forEach(c -> setNameForPrivateConversation(username, c));
        return conversationMapper.toDTOs(conversations);
    }

    private void setNameForPrivateConversation(String curUsername, Conversation conversation) {
        if (conversation == null) {
            return;
        }
        String name = conversation.getName();
        String recipientUsername = ConversationUtils.getRemainderUser(name, curUsername);
        conversation.setName(recipientUsername);
    }
}
