package com.vanannek.restcontroller;

import com.vanannek.dto.ChatMessageDTO;
import com.vanannek.service.message.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/messages")
public class MessageRestController {

    @Autowired private MessageService messageService;

    @GetMapping("/get-messages")
    public List<ChatMessageDTO> getMessages(@RequestParam("conversationId") String conversationId) {
        List<ChatMessageDTO> msgs = messageService.getMessages(conversationId);
        return msgs;
    }
}
