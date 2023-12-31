package com.vanannek.socialmedia.post;

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
}