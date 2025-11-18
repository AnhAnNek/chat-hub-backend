package com.vanannek.socialmedia.postreaction;

public class DuplicatePostReactionException extends RuntimeException {
    public DuplicatePostReactionException(String message) {
        super(message);
    }
}
