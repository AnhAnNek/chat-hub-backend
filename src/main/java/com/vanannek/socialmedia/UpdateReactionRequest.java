package com.vanannek.socialmedia;

public record UpdateReactionRequest(
        Long id,
        EReactionType type
) {}
