package com.vanannek.socialmedia.commentreaction;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CommentReactionDTO {
    private Long id;
    private String username;
    private Long commentId;
    private String type;

    public CommentReactionDTO(String username, String type) {
        this.username = username;
        this.type = type;
    }
}
