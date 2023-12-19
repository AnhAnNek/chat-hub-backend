package com.vanannek.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.vanannek.entity.Conversation;
import com.vanannek.entity.ConversationMember;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.time.Duration;
import java.time.LocalDateTime;
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

    public String getLastTimeAgo() {
        if (lastMessageDTO == null || lastMessageDTO.getSendingTime() == null) {
            return "";
        }

        Timestamp sendingTime = lastMessageDTO.getSendingTime();
        LocalDateTime currentTime = LocalDateTime.now();
        LocalDateTime messageTime = sendingTime.toLocalDateTime();
        Duration duration = Duration.between(currentTime, messageTime);

        long days = duration.toDays();
        long hours = duration.toHours() % 24;
        long minutes = duration.toMinutes() % 60;

        StringBuilder result = new StringBuilder();

        if (days > 0) {
            result.append(days).append(" day").append(days > 1 ? "s" : "");
        } else if (hours > 0) {
            result.append(hours).append(" hour").append(hours > 1 ? "s" : "");
        } else if (minutes > 0) {
            result.append(minutes).append(" minute").append(minutes > 1 ? "s" : "");
        } else {
            result.append("just now");
        }

        result.append(" ago");

        return result.toString();
    }
}
