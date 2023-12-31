package com.vanannek.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.*;

@Entity
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@EqualsAndHashCode(exclude = {"sentMessages", "memberships"})
@ToString(exclude = {"sentMessages", "memberships"})
public class User {
    public enum EGender {
        MALE, FEMALE
    }

    @Id
    private String username;

    @Column(name = "pass_hash")
    private String passHash;

    @Column(name = "full_name")
    private String fullName;

    @Column(unique = true)
    private String email;

    @Enumerated(EnumType.STRING)
    private EGender gender;

    @Column(name = "is_deleted")
    private boolean isDeleted;

    @OneToMany(mappedBy = "sender", fetch = FetchType.LAZY)
    private List<ChatMessage> sentMessages = new ArrayList<>();

    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY)
    private Set<ConversationMember> memberships = new HashSet<>();

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, orphanRemoval = true)
    private List<Notification> notifications = new ArrayList<>();
}
