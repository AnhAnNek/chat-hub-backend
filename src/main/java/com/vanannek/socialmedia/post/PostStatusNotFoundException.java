package com.vanannek.socialmedia.post;

public class PostStatusNotFoundException extends RuntimeException {
    public PostStatusNotFoundException(String message) {
        super(message);
    }
}
