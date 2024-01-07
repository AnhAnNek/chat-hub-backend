package com.vanannek.websocket;

import com.vanannek.chat.message.ChatMessageDTO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.Map;

@Controller
public class WebsocketController {

    private static final Logger log = LogManager.getLogger(WebsocketController.class);

    @Autowired private CustomMessageHandler messageHandler;

    @MessageMapping("/chat.sendMessage")
    private void sendMessage(@Payload ChatMessageDTO chatMessageDTO) {
        log.info("`{}` send msg to conversation `{}`, content: `{}`",
                chatMessageDTO.getSenderUsername(), chatMessageDTO.getConversationId(), chatMessageDTO);
        messageHandler.saveAndSendMessage(chatMessageDTO);
    }

    @MessageMapping("/chat.addUser/{senderUsername}")
    public void addUserByConversation(@DestinationVariable String senderUsername,
                                      SimpMessageHeaderAccessor headerAccessor) {
        OnlineUserStore onlineUserStore = OnlineUserStore.getIns();

        String sessionId = headerAccessor.getSessionId();
        log.info("User Connected: `{}`, sessionsId: `{}`", senderUsername, sessionId);

        if (senderUsername == null) {
            return;
        }

        Map<String, Object> sessionAttributes = headerAccessor.getSessionAttributes();
        log.info("Before user connect: `{}`", sessionAttributes);
        sessionAttributes.put("senderUsername", senderUsername);
        log.info("After user connect: `{}`", sessionAttributes);

        onlineUserStore.add(senderUsername, sessionId);
        List<String> onlineUsers = onlineUserStore.getOnlineUsers();
        messageHandler.sendOnlineUsers(onlineUsers);
    }
}
