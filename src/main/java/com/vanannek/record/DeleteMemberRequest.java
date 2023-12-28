package com.vanannek.record;

public record DeleteMemberRequest(
        String memberUsername,
        String conversationId
) {}
