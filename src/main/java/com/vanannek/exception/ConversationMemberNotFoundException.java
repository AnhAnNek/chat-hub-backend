package com.vanannek.exception;

public class ConversationMemberNotFoundException extends RuntimeException {
    public ConversationMemberNotFoundException(String message) {
        super(message);
    }
}
