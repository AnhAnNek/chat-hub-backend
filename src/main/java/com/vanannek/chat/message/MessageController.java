package com.vanannek.chat.message;

import com.vanannek.chat.message.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/messages")
public class MessageController {

    @Autowired private MessageService messageService;

    @GetMapping("/get-messages")
    public List<ChatMessageDTO> getMessages(@RequestParam("conversationId") String conversationId) {
        List<ChatMessageDTO> msgs = messageService.getMessages(conversationId);
        return msgs;
    }
}
