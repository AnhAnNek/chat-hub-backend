package com.vanannek.chat.conversation;

public class DuplicatePrivateConversationException extends RuntimeException {
    public DuplicatePrivateConversationException(String message) {
        super(message);
    }
}
