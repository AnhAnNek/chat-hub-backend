package com.vanannek.chat.member;

public record DeleteMemberRequest(
        String memberUsername,
        String conversationId
) {}
