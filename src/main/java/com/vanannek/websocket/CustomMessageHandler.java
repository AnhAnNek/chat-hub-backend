package com.vanannek.websocket;

import com.vanannek.dto.ChatMessageDTO;
import com.vanannek.service.message.MessageService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CustomMessageHandler {

    private static final Logger log = LogManager.getLogger(CustomMessageHandler.class);
    private static final String RECEIVING_DEST_PATTERN = "/topic/messages/%s";
    private static final String ONLINE_USERS_DEST = "/topic/online-users";

    @Autowired private SimpMessagingTemplate messagingTemplate;
    @Autowired private MessageService messageService;

    public void saveAndSendMessage(ChatMessageDTO chatMessageDTO) {
        try {
            String receivingDest = String.format(RECEIVING_DEST_PATTERN, chatMessageDTO.getConversationId());
            ChatMessageDTO savedDTO = messageService.add(chatMessageDTO);
            messagingTemplate.convertAndSend(receivingDest, savedDTO);
        } catch (Exception e) {
            log.error("Error during saving and sending the message: {}", e.getMessage());
        }
    }

    public void sendOnlineUsers(List<String> onlineUsers) {
        messagingTemplate.convertAndSend(ONLINE_USERS_DEST, onlineUsers);
    }
}
