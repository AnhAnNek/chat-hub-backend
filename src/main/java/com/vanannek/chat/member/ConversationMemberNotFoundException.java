package com.vanannek.chat.member;

public class ConversationMemberNotFoundException extends RuntimeException {
    public ConversationMemberNotFoundException(String message) {
        super(message);
    }
}
