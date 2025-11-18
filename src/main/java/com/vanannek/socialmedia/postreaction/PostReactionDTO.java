package com.vanannek.socialmedia.postreaction;

import com.vanannek.socialmedia.EReactionType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class PostReactionDTO {
    private Long id;
    private String username;
    private Long postId;
    private EReactionType type;

    public PostReactionDTO(String username, EReactionType type) {
        this.username = username;
        this.type = type;
    }
}