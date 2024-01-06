package com.vanannek.socialmedia.post;

public class UpsupportedPostStatusException extends RuntimeException {
    public UpsupportedPostStatusException(String message) {
        super(message);
    }
}
