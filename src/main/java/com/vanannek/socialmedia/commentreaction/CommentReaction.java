package com.vanannek.socialmedia.commentreaction;

import com.vanannek.socialmedia.EReactionType;
import com.vanannek.socialmedia.comment.Comment;
import com.vanannek.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "comment_reactions")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentReaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "username", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "comment_id", nullable = false)
    private Comment comment;

    @Enumerated(EnumType.STRING)
    private EReactionType type;

    @Column(name = "is_deleted")
    private boolean isDeleted;

    @Transient
    private String username;
}
