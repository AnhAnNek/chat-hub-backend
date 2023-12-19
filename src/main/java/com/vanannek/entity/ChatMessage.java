package com.vanannek.entity;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;

@Entity
@Table(name = "chat_messages")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@EqualsAndHashCode(exclude = {"conversation"})
@ToString(exclude = "conversation")
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
    private Timestamp sendingTime;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "sender_username")
    private User sender;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name = "conversation_id", nullable = false)
    private Conversation conversation;

    @Transient
    private String senderUsername;

    @Transient
    private String conversationId;
}
