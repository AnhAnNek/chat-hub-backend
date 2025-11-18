package com.vanannek.socialmedia.post;

import java.time.LocalDateTime;

public record UpdatePostRequest(
        Long id,
        String content,
        LocalDateTime updatedAt,
        Post.EStatus status
) {}
