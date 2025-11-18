package com.vanannek.socialmedia.comment;

import java.time.LocalDateTime;

public record UpdateCommentRequest(
        Long id,
        String content,
        LocalDateTime updatedAt
) {}
