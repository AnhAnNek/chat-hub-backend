package com.vanannek.chat.conversation;

import com.vanannek.chat.conversation.service.ConversationService;
import com.vanannek.chat.member.ConversationMember;
import com.vanannek.chat.member.ConversationMemberDTO;
import com.vanannek.chat.member.DeleteMemberRequest;
import com.vanannek.chat.member.service.ConversationMemberService;
import com.vanannek.chat.message.ChatMessage;
import com.vanannek.chat.message.ChatMessageDTO;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/conversations")
@RequiredArgsConstructor
public class ConversationController {

    private static final Logger log = LogManager.getLogger(ConversationController.class);

    private final ConversationService conversationService;
    private final ConversationMemberService memberService;

    @GetMapping("/get-conversations")
    public ResponseEntity<List<ConversationDTO>> getConversationsByUsername(@RequestParam("username") String username) {
        List<ConversationDTO> conversations = conversationService.getConversationsByUsername(username);
        conversations.sort((obj1, obj2) -> obj2.getLastMessageDTO().getSendingTime().compareTo(obj1.getLastMessageDTO().getSendingTime()));
        log.info(ConversationUtils.RETRIEVED_CONVERSATIONS, username, conversations.size());
        return ResponseEntity.ok(conversations);
    }

    @DeleteMapping("/delete-conversation")
    private ResponseEntity<String> deleteConversation(@RequestParam String conversationId) {
        conversationService.deleteById(conversationId);
        log.info(ConversationUtils.CONVERSATION_DELETED_SUCCESSFULLY);
        return ResponseEntity.ok(ConversationUtils.CONVERSATION_DELETED_SUCCESSFULLY);
    }

    @PostMapping("/add-new-member")
    public ResponseEntity<String> addNewMember(@RequestBody ConversationMemberDTO memberDTO) {
        memberService.add(memberDTO);
        log.info(ConversationUtils.MEMBER_ADDED_SUCCESSFULLY);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ConversationUtils.MEMBER_ADDED_SUCCESSFULLY);
    }

    @DeleteMapping("/delete-member")
    public ResponseEntity<String> deleteMember(@RequestBody DeleteMemberRequest deleteMemberRequest) {
        memberService.deleteByUsername(deleteMemberRequest.conversationId(), deleteMemberRequest.memberUsername());
        log.info(ConversationUtils.MEMBER_DELETED_SUCCESSFULLY);
        return ResponseEntity.ok(ConversationUtils.MEMBER_DELETED_SUCCESSFULLY);
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
        log.info(ConversationUtils.PRIVATE_CONVERSATION_ADDED_SUCCESSFULLY);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ConversationUtils.PRIVATE_CONVERSATION_ADDED_SUCCESSFULLY);
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
        log.info(ConversationUtils.GROUP_ADDED_SUCCESSFULLY);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ConversationUtils.GROUP_ADDED_SUCCESSFULLY);
    }
}
