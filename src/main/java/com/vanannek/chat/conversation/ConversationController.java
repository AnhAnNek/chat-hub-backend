package com.vanannek.chat.conversation;

import com.vanannek.chat.member.ConversationMember;
import com.vanannek.chat.member.ConversationMemberDTO;
import com.vanannek.chat.member.DeleteMemberRequest;
import com.vanannek.chat.message.ChatMessageDTO;
import com.vanannek.chat.message.ChatMessage;
import com.vanannek.chat.conversation.service.ConversationService;
import com.vanannek.chat.member.service.ConversationMemberService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/conversations")
public class ConversationController {

    private static final Logger log = LogManager.getLogger(ConversationController.class);

    @Autowired private ConversationService conversationService;
    @Autowired private ConversationMemberService memberService;

    @GetMapping("/get-conversations")
    public ResponseEntity<List<ConversationDTO>> getConversationsByUsername(@RequestParam("username") String username) {
        List<ConversationDTO> conversations = conversationService.getConversationsByUsername(username);
        conversations.sort((obj1, obj2) -> obj2.getLastMessageDTO().getSendingTime().compareTo(obj1.getLastMessageDTO().getSendingTime()));
        if (conversations.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(conversations, HttpStatus.OK);
    }

    @DeleteMapping("/delete-conversation")
    private ResponseEntity<String> deleteConversation(@RequestParam String conversationId) {
        try {
            conversationService.deleteById(conversationId);
            return new ResponseEntity<>("Conversation deleted successfully", HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/add-new-member")
    public ResponseEntity<String> addNewMember(@RequestBody ConversationMemberDTO memberDTO) {
        try {
            memberService.add(memberDTO);
            return new ResponseEntity<>("Member added successfully", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/delete-member")
    public ResponseEntity<String> deleteMember(@RequestBody DeleteMemberRequest deleteMemberRequest) {
        try {
            memberService.deleteByUsername(deleteMemberRequest.conversationId(), deleteMemberRequest.memberUsername());
            return new ResponseEntity<>("Member deleted successfully", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/add-private-conversation")
    public ResponseEntity<String> addPrivateConversation(
            @RequestParam("curUsername") String curUsername,
            @RequestParam("targetUsername") String targetUsername) {

        String name = ConversationUtils.getPrivateConversationName(curUsername, targetUsername);
        ConversationDTO conversationDTO = ConversationDTO.builder()
                .type(Conversation.EType.PRIVATE)
                .name(name)
                .build();

        conversationDTO.addMemberDTO(ConversationMemberDTO.builder()
                .memberUsername(curUsername)
                .role(ConversationMember.ERole.MEMBER)
                .build());
        conversationDTO.addMemberDTO(ConversationMemberDTO.builder()
                .memberUsername(targetUsername)
                .role(ConversationMember.ERole.MEMBER)
                .build());

        ChatMessageDTO chatMessageDTO = ChatMessageDTO.builder()
                .content("Start new conversation")
                .type(ChatMessage.EType.NOTIFICATION)
                .sendingTime(LocalDateTime.now())
                .senderUsername(curUsername)
                .build();

        conversationDTO.addMessageDTO(chatMessageDTO);

        conversationService.saveConversationWithMessagesAndMembers(conversationDTO);
        return new ResponseEntity<>("A private conversation added successfully", HttpStatus.CREATED);
    }

    @PostMapping("/add-group")
    public ResponseEntity<String> addGroup(@RequestBody AddGroupRequest addGroupRequest) {
        String curUsername = addGroupRequest.curUsername();

        ConversationDTO conversationDTO = ConversationDTO.builder()
                .type(Conversation.EType.GROUP)
                .name(addGroupRequest.conversationName())
                .build();

        conversationDTO.addMemberDTO(ConversationMemberDTO.builder()
                .memberUsername(curUsername)
                .role(ConversationMember.ERole.ADMIN)
                .build());

        for (String username : addGroupRequest.memberUsernames()) {
            conversationDTO.addMemberDTO(ConversationMemberDTO.builder()
                    .memberUsername(username)
                    .role(ConversationMember.ERole.MEMBER)
                    .build());
        }

        String msg = String.format("`%s` created a new group", curUsername);
        ChatMessageDTO chatMessageDTO = ChatMessageDTO.builder()
                .content(msg)
                .type(ChatMessage.EType.NOTIFICATION)
                .sendingTime(LocalDateTime.now())
                .senderUsername(curUsername)
                .build();

        conversationDTO.addMessageDTO(chatMessageDTO);

        conversationService.saveConversationWithMessagesAndMembers(conversationDTO);

        return new ResponseEntity<>("A group added successfully", HttpStatus.CREATED);
    }
}
