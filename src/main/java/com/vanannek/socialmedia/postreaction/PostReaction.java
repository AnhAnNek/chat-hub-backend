package com.vanannek.socialmedia.postreaction;

import com.vanannek.socialmedia.EReactionType;
import com.vanannek.socialmedia.post.Post;
import com.vanannek.user.User;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "post_reactions")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostReaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "username", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Post post;

    @Enumerated(EnumType.STRING)
    private EReactionType type;

    @Column(name = "is_deleted")
    private boolean isDeleted;

    @Transient
    private String username;

    @Transient
    private Long postId;
}
