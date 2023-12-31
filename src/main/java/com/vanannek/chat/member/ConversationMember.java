package com.vanannek.chat.member;

import com.vanannek.chat.conversation.Conversation;
import com.vanannek.user.User;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "conversation_members")
@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(exclude = {"conversation"})
@ToString(exclude = "conversation")
public class ConversationMember {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "member_username", nullable = false)
    private User member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "conversation_id", nullable = false)
    private Conversation conversation;

    @Enumerated(EnumType.STRING)
    private ERole role;

    @Transient
    private String memberUsername;

    @Transient
    private String conversationId;

    public ConversationMember(User member, ERole role) {
        this.member = member;
        this.role = role;
    }

    public enum ERole {
        MEMBER("Member", "Regular group member"),
        MODERATOR("Moderator", "Moderator with some administrative privileges"),
        ADMIN("Administrator", "Administrator with full administrative privileges");

        private final String displayName;
        private final String description;

        ERole(String displayName, String description) {
            this.displayName = displayName;
            this.description = description;
        }

        public String getDisplayName() {
            return displayName;
        }

        public String getDescription() {
            return description;
        }

        public boolean hasModeratorPrivileges() {
            return this == MODERATOR || this == ADMIN;
        }
    }
}
