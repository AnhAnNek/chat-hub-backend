package com.vanannek.socialmedia.commentreaction;

public class DuplicateCommentReactionException extends RuntimeException {
    public DuplicateCommentReactionException(String message) {
        super(message);
    }
}
