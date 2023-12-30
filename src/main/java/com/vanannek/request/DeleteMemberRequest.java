package com.vanannek.request;

public record DeleteMemberRequest(
        String memberUsername,
        String conversationId
) {}
