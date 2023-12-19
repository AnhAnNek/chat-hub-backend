package com.vanannek.listener;

import com.vanannek.dto.ChatMessageDTO;
import com.vanannek.entity.ChatMessage;
import com.vanannek.handler.CustomMessageHandler;
import com.vanannek.store.OnlineUserStore;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Map;

@Component
public class WebSocketEventListener {

    private static final Logger log = LogManager.getLogger(WebSocketEventListener.class);

    @Autowired private CustomMessageHandler messageHandler;

    @EventListener
    public void handleWebsocketConnectListener(SessionConnectedEvent event) {
        log.info("Received a new web socket connection");
    }

    @EventListener
    public void handleWebsocketDisconnectListener(SessionDisconnectEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        OnlineUserStore onlineUserStore = OnlineUserStore.getIns();
        Map<String, Object> sessionAttributes = headerAccessor.getSessionAttributes();

        String senderUsername = (String) sessionAttributes.get("senderUsername");
        String conversationId = (String) sessionAttributes.get("conversationId");

        if (senderUsername == null || conversationId == null) {
            return;
        }

        log.info("User Disconnected: " + senderUsername);
        log.info("headerAccessor.SessionId = " + headerAccessor.getSessionId());

        removeSessionAttributes(sessionAttributes, "senderUsername", "conversationId");

        onlineUserStore.remove(senderUsername, headerAccessor.getSessionId());
        List<String> onlineUsers = onlineUserStore.getOnlineUsers();
        messageHandler.sendOnlineUsers(onlineUsers);
    }

    private void removeSessionAttributes(Map<String, Object> sessionAttributes, String... attributes) {
        for (String attribute : attributes) {
            sessionAttributes.remove(attribute);
        }
        log.info("Session Attributes: {}", sessionAttributes);
    }
}
