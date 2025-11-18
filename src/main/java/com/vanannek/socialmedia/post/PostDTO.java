package com.vanannek.socialmedia.post;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.vanannek.chat.member.ConversationMemberDTO;
import com.vanannek.chat.message.ChatMessageDTO;
import com.vanannek.socialmedia.comment.CommentDTO;
import com.vanannek.socialmedia.postreaction.PostReactionDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class PostDTO {
    private Long id;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Post.EStatus status;
    private String username;

    private List<PostReactionDTO> reactions;

    @JsonIgnore
    private List<CommentDTO> comments;

    public void addReactions(List<PostReactionDTO> postReactions) {
        postReactions.forEach(this::addReaction);
    }

    public void addReaction(PostReactionDTO postReaction) {
        if (reactions == null) {
            reactions = new ArrayList<>();
        }
        reactions.add(postReaction);
    }

    public void addComments(List<CommentDTO> comments) {
        comments.forEach(this::addComment);
    }

    public void addComment(CommentDTO comment) {
        if (comments == null) {
            comments = new ArrayList<>();
        }
        comments.add(comment);
    }
}
