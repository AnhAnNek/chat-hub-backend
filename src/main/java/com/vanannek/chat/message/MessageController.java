package com.vanannek.chat.message;

import com.vanannek.chat.message.service.MessageService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/messages")
public class MessageController {

    private static final Logger log = LogManager.getLogger(MessageController.class);

    @Autowired private MessageService messageService;

    @GetMapping("/get-messages")
    public ResponseEntity<List<ChatMessageDTO>> getMessages(@RequestParam("conversationId") String conversationId) {
        List<ChatMessageDTO> messageDTOs = messageService.getMessages(conversationId);
        log.info("Retrieved messages for conversation '{}'. Count: {}", conversationId, messageDTOs.size());
        return ResponseEntity.ok(messageDTOs);
    }
}
