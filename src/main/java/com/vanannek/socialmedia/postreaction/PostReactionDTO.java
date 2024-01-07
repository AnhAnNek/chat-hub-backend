package com.vanannek.socialmedia.postreaction;

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
    private String type;

    public PostReactionDTO(String username, String type) {
        this.username = username;
        this.type = type;
    }
}