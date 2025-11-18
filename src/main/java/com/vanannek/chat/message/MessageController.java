package com.vanannek.chat.message;

import com.vanannek.chat.message.service.MessageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/messages")
@Tag(name = "Message")
public class MessageController {

    private static final Logger log = LogManager.getLogger(MessageController.class);

    @Autowired private MessageService messageService;

    @Operation(
            summary = "Retrieve messages",
            description = "Retrieve messages by providing the conversation id.",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "Unauthorized / Invalid Token",
                            responseCode = "401"
                    )
            }
    )
    @GetMapping("/get-messages")
    public ResponseEntity<List<ChatMessageDTO>> getMessages(@RequestParam("conversationId") String conversationId) {
        List<ChatMessageDTO> messageDTOs = messageService.getMessages(conversationId);
        log.info("Retrieved messages for conversation '{}'. Count: {}", conversationId, messageDTOs.size());
        return ResponseEntity.ok(messageDTOs);
    }
}
