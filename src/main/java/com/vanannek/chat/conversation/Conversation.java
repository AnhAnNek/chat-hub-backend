package com.vanannek.chat.conversation;

import com.vanannek.chat.member.ConversationMember;
import com.vanannek.chat.message.ChatMessage;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import java.util.*;

@Entity
@Table(name = "conversations")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class Conversation {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "id", columnDefinition = "VARCHAR(36)")
    private String id;

    @Enumerated(EnumType.STRING)
    private EType type;

    private String name;

    @Column(name = "is_deleted")
    private boolean isDeleted;

    @OneToMany(mappedBy = "conversation", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<ChatMessage> chatMessages = new ArrayList<>();

    @OneToMany(mappedBy = "conversation", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private Set<ConversationMember> members = new HashSet<>();

    public Conversation(EType type, String name) {
        this.type = type;
        this.name = name;
    }

    public enum EType {
        PRIVATE, GROUP
    }
}
