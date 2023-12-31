package com.vanannek.chat.message.service;

import com.vanannek.chat.message.ChatMessageDTO;
import com.vanannek.chat.message.ChatMessage;
import com.vanannek.chat.conversation.Conversation;
import com.vanannek.user.User;
import com.vanannek.chat.conversation.ConversationNotFoundException;
import com.vanannek.user.UserNotFoundException;
import com.vanannek.chat.message.MessageMapper;
import com.vanannek.chat.message.ChatMessageRepos;
import com.vanannek.chat.conversation.ConversationRepos;
import com.vanannek.user.UserRepos;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class MessageServiceImpl implements MessageService {

    @Autowired private ConversationRepos conversationRepos;
    @Autowired private ChatMessageRepos messageRepos;
    @Autowired private UserRepos userRepos;
    private MessageMapper messageMapper = MessageMapper.INSTANCE;

    @Override
    public ChatMessageDTO add(ChatMessageDTO cmDTO) {
        ChatMessage cm = messageMapper.toEntity(cmDTO);
        setReferenceObjects(cmDTO, cm);
        ChatMessage savedCM = messageRepos.save(cm);
        return messageMapper.toDTO(savedCM);
    }

    private void setReferenceObjects(ChatMessageDTO cmDTO, ChatMessage cm) {
        String senderUsername = cmDTO.getSenderUsername();
        User sender = userRepos
                .findById(senderUsername)
                .orElseThrow(() -> new UserNotFoundException("Could not find any user with username=" + senderUsername));
        cm.setSender(sender);

        String conversationId = cmDTO.getConversationId();
        Conversation conversation = conversationRepos.findById(conversationId)
                .orElseThrow(() -> new ConversationNotFoundException("Could not find any conversation with id=" + conversationId));
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
        List<ChatMessage> msgs = messageRepos.getMessages(conversationId);
        return messageMapper.toDTOs(msgs);
    }
}
