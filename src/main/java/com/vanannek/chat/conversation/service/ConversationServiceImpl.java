package com.vanannek.chat.conversation.service;

import com.vanannek.chat.conversation.*;
import com.vanannek.chat.member.ConversationMemberRepos;
import com.vanannek.user.User;
import com.vanannek.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ConversationServiceImpl implements ConversationService {

    private final ConversationMapper conversationMapper = ConversationMapper.INSTANCE;

    private final ConversationRepos conversationRepos;
    private final ConversationMemberRepos memberRepos;
    private final UserService userService;

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

            User sender = userService.getUserByUsername(senderUsername);
            chatMessage.setSender(sender);
            chatMessage.setConversation(conversation);
        });

        conversation.getMembers().forEach(conversationMember -> {
            if (conversationMember == null) {
                return;
            }

            String memberUsername = conversationMember.getMemberUsername();
            User member = userService.getUserByUsername(memberUsername);
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
        Conversation conversation = getConversationById(conversationId);
        return conversationMapper.toDTO(conversation);
    }

    @Override
    public List<ConversationDTO> getConversationsByUsername(String username) {
        userService.getUserByUsername(username);

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

    @Override
    public Conversation getConversationById(String conversationId) {
        return conversationRepos.findById(conversationId)
                .orElseThrow(() -> new ConversationNotFoundException("Could not find any conversation"));
    }

    @Override
    public boolean existsPrivateConversation(String firstUser, String secondUser) {
        return conversationRepos.existsPrivateConversation(firstUser, secondUser);
    }
}
