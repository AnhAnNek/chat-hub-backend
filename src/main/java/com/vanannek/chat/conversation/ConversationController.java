package com.vanannek.chat.conversation;

import com.vanannek.chat.conversation.service.ConversationService;
import com.vanannek.chat.member.ConversationMember;
import com.vanannek.chat.member.ConversationMemberDTO;
import com.vanannek.chat.member.DeleteMemberRequest;
import com.vanannek.chat.member.service.ConversationMemberService;
import com.vanannek.chat.message.ChatMessage;
import com.vanannek.chat.message.ChatMessageDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Conversation")
public class ConversationController {

    private static final Logger log = LogManager.getLogger(ConversationController.class);

    private final ConversationService conversationService;
    private final ConversationMemberService memberService;

    @Operation(
            summary = "Retrieve conversations by username",
            description = "Retrieve conversations by providing the username.",
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
    @GetMapping("/get-conversations")
    public ResponseEntity<List<ConversationDTO>> getConversationsByUsername(@RequestParam("username") String username) {
        List<ConversationDTO> conversations = conversationService.getConversationsByUsername(username);
        conversations.sort((obj1, obj2) -> obj2.getLastMessageDTO().getSendingTime().compareTo(obj1.getLastMessageDTO().getSendingTime()));
        log.info(ConversationUtils.RETRIEVED_CONVERSATIONS, username, conversations.size());
        return ResponseEntity.ok(conversations);
    }

    @Operation(
            summary = "Delete conversation",
            description = "Delete a conversation by providing the conversation id.",
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
    @DeleteMapping("/delete-conversation")
    private ResponseEntity<String> deleteConversation(@RequestParam String conversationId) {
        conversationService.deleteById(conversationId);
        log.info(ConversationUtils.CONVERSATION_DELETED_SUCCESSFULLY);
        return ResponseEntity.ok(ConversationUtils.CONVERSATION_DELETED_SUCCESSFULLY);
    }

    @Operation(
            summary = "Add new member to conversation",
            description = "Add a new member to a conversation by providing the member details.",
            responses = {
                    @ApiResponse(
                            description = "Created",
                            responseCode = "201"
                    ),
                    @ApiResponse(
                            description = "Unauthorized / Invalid Token",
                            responseCode = "401"
                    )
            }
    )
    @PostMapping("/add-new-member")
    public ResponseEntity<String> addNewMember(@RequestBody ConversationMemberDTO memberDTO) {
        memberService.add(memberDTO);
        log.info(ConversationUtils.MEMBER_ADDED_SUCCESSFULLY);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ConversationUtils.MEMBER_ADDED_SUCCESSFULLY);
    }

    @Operation(
            summary = "Delete a member from a conversation",
            description = "Delete a member from a conversation by providing the member details.",
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
    @DeleteMapping("/delete-member")
    public ResponseEntity<String> deleteMember(@RequestBody DeleteMemberRequest deleteMemberRequest) {
        memberService.deleteByUsername(deleteMemberRequest.conversationId(), deleteMemberRequest.memberUsername());
        log.info(ConversationUtils.MEMBER_DELETED_SUCCESSFULLY);
        return ResponseEntity.ok(ConversationUtils.MEMBER_DELETED_SUCCESSFULLY);
    }

    @Operation(
            summary = "Add a private conversation",
            description = "Add a private conversation by providing the two usernames.",
            responses = {
                    @ApiResponse(
                            description = "Created",
                            responseCode = "201"
                    ),
                    @ApiResponse(
                            description = "Unauthorized / Invalid Token",
                            responseCode = "401"
                    )
            }
    )
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

        if (conversationService.existsPrivateConversation(curUsername, targetUsername)) {
            throw new DuplicatePrivateConversationException("A private conversation already between the users");
        }

        conversationService.saveConversationWithMessagesAndMembers(conversationDTO);
        log.info(ConversationUtils.PRIVATE_CONVERSATION_ADDED_SUCCESSFULLY);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ConversationUtils.PRIVATE_CONVERSATION_ADDED_SUCCESSFULLY);
    }

    @Operation(
            summary = "Add a group",
            description = "Add a group by providing the group details.",
            responses = {
                    @ApiResponse(
                            description = "Created",
                            responseCode = "201"
                    ),
                    @ApiResponse(
                            description = "Unauthorized / Invalid Token",
                            responseCode = "401"
                    )
            }
    )
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
