package com.vanannek.chat.message;

import com.vanannek.chat.conversation.Conversation;
import com.vanannek.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "chat_messages")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class ChatMessage {
    public enum EType {
        CHAT,
        NOTIFICATION
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT")
    private String content;

    @Enumerated(EnumType.STRING)
    private EType type;

    @Column(name = "sending_time")
    private LocalDateTime sendingTime;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "sender_username")
    private User sender;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name = "conversation_id", nullable = false)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Conversation conversation;

    @Transient
    private String senderUsername;

    @Transient
    private String conversationId;
}
