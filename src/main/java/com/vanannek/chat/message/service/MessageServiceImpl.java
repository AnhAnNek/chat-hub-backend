package com.vanannek.chat.message.service;

import com.vanannek.chat.conversation.Conversation;
import com.vanannek.chat.conversation.service.ConversationService;
import com.vanannek.chat.message.ChatMessage;
import com.vanannek.chat.message.ChatMessageDTO;
import com.vanannek.chat.message.ChatMessageRepos;
import com.vanannek.chat.message.MessageMapper;
import com.vanannek.user.User;
import com.vanannek.user.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class MessageServiceImpl implements MessageService {

    private final MessageMapper messageMapper = MessageMapper.INSTANCE;

    private final ChatMessageRepos messageRepos;
    private final ConversationService conversationService;
    private final UserService userService;

    @Override
    public ChatMessageDTO add(ChatMessageDTO cmDTO) {
        ChatMessage cm = messageMapper.toEntity(cmDTO);
        setReferenceObjects(cmDTO, cm);
        ChatMessage savedCM = messageRepos.save(cm);
        return messageMapper.toDTO(savedCM);
    }

    private void setReferenceObjects(ChatMessageDTO cmDTO, ChatMessage cm) {
        String senderUsername = cmDTO.getSenderUsername();
        User sender = userService.getUserByUsername(senderUsername);
        cm.setSender(sender);

        String conversationId = cmDTO.getConversationId();
        Conversation conversation = conversationService.getConversationById(conversationId);
        cm.setConversation(conversation);
    }

    @Override
    public List<ChatMessageDTO> addAll(List<ChatMessageDTO> cmDTOs) {
        List<ChatMessageDTO> chatMessageDTOS = new ArrayList<>();
        cmDTOs.forEach(cmDTO -> {
            ChatMessageDTO saved = add(cmDTO);
            chatMessageDTOS.add(saved);
        });
        return chatMessageDTOS;
    }

    @Override
    public List<ChatMessageDTO> getMessages(String conversationId) {
        conversationService.getConversationById(conversationId);

        List<ChatMessage> msgs = messageRepos.getMessages(conversationId);
        return messageMapper.toDTOs(msgs);
    }
}
