package com.vanannek.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.vanannek.entity.Comment;
import com.vanannek.entity.Post;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class PostDTO {
    private Long id;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Post.EStatus status;
    private String username;

    @JsonIgnore
    private List<PostReactionDTO> reactionDTOs = new ArrayList<>();

    @JsonIgnore
    private List<Comment> comments = new ArrayList<>();
}
