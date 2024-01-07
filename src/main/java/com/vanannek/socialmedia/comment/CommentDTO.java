package com.vanannek.socialmedia.comment;

import com.vanannek.socialmedia.commentreaction.CommentReactionDTO;
import com.vanannek.socialmedia.postreaction.PostReactionDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class CommentDTO {
    private Long id;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Long postId;
    private String username;

    private List<CommentReactionDTO> reactions;

    public void addReactions(List<CommentReactionDTO> postReactions) {
        postReactions.forEach(this::addReaction);
    }

    public void addReaction(CommentReactionDTO postReaction) {
        if (reactions == null) {
            reactions = new ArrayList<>();
        }
        reactions.add(postReaction);
    }
}