package com.vanannek.chat.conversation;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.vanannek.chat.member.ConversationMemberDTO;
import com.vanannek.chat.message.ChatMessageDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class ConversationDTO {
    private String id;
    private Conversation.EType type;
    private String name;
    private ChatMessageDTO lastMessageDTO;
    private int unreadMessages = 0;

    @JsonIgnore
    private List<ChatMessageDTO> chatMessageDTOs = new ArrayList<>();

    @JsonIgnore
    private Set<ConversationMemberDTO> memberDTOs = new HashSet<>();

    public void addMessageDTOs(List<ChatMessageDTO> chatMessageDTOs) {
        chatMessageDTOs.forEach(this::addMessageDTO);
    }

    public void addMessageDTO(ChatMessageDTO chatMessageDTO) {
        if (chatMessageDTOs == null) {
            chatMessageDTOs = new ArrayList<>();
        }
        chatMessageDTOs.add(chatMessageDTO);
    }

    public void addMemberDTOs(Set<ConversationMemberDTO> memberDTOs) {
        memberDTOs.forEach(this::addMemberDTO);
    }

    public void addMemberDTO(ConversationMemberDTO memberDTO) {
        if (memberDTOs == null) {
            memberDTOs = new HashSet<>();
        }
        memberDTOs.add(memberDTO);
    }
}
