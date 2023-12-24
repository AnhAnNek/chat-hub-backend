package com.vanannek.websocket;

import com.vanannek.dto.ChatMessageDTO;
import com.vanannek.handler.CustomMessageHandler;
import com.vanannek.store.OnlineUserStore;
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
        String content = "`{}` send msg to conversation `{}`, content: {}";
        log.info(content, chatMessageDTO.getSenderUsername(),
                chatMessageDTO.getConversationId(), chatMessageDTO);
        messageHandler.saveAndSendMessage(chatMessageDTO);
    }

    @MessageMapping("/chat.addUser/{senderUsername}")
    public void addUserByConversation(@Payload ChatMessageDTO chatMessageDTO,
                                      @DestinationVariable String senderUsername,
                                      SimpMessageHeaderAccessor headerAccessor) {
        OnlineUserStore onlineUserStore = OnlineUserStore.getIns();
        log.info("Add new user, session Id: `{}`", headerAccessor.getSessionId());

        if (senderUsername == null) {
            return;
        }

        Map<String, Object> sessionAttributes = headerAccessor.getSessionAttributes();
        log.info("Before put new user: `{}`", sessionAttributes);
        sessionAttributes.put("senderUsername", senderUsername);
        sessionAttributes.put("conversationId", chatMessageDTO.getConversationId());
        log.info("After put new user: `{}`", sessionAttributes);

        onlineUserStore.add(senderUsername, headerAccessor.getSessionId());
        List<String> onlineUsers = onlineUserStore.getOnlineUsers();
        messageHandler.sendOnlineUsers(onlineUsers);
    }
}
