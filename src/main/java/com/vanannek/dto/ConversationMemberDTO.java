package com.vanannek.dto;

import com.vanannek.entity.ConversationMember;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@EqualsAndHashCode
public class ConversationMemberDTO {
    private Long id;
    private String memberUsername;
    private String conversationId;
    private ConversationMember.ERole role;

    public ConversationMemberDTO(String memberUsername, ConversationMember.ERole role) {
        this.memberUsername = memberUsername;
        this.role = role;
    }
}
