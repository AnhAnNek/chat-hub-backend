package com.vanannek.exceptionhandler;

import com.vanannek.socialmedia.post.PostStatusNotFoundException;
import com.vanannek.socialmedia.postreaction.DuplicatePostReactionException;
import com.vanannek.socialmedia.post.PostNotFoundException;
import com.vanannek.socialmedia.postreaction.PostReactionNotFoundException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class SocialMediaExceptionHandler {

    private static final Logger log = LogManager.getLogger(SocialMediaExceptionHandler.class);

    @ExceptionHandler(PostNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<String> handlePostNotFound(PostNotFoundException e) {
        log.error("Post not found", e);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

    @ExceptionHandler(PostStatusNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<String> handlePostNotFound(PostStatusNotFoundException e) {
        log.error("Post status not found", e);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

    @ExceptionHandler(DuplicatePostReactionException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<String> handleDuplicatePostReaction(DuplicatePostReactionException e) {
        log.error("Duplicate post reaction", e);
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler(PostReactionNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<String> handlePostReactionNotFound(PostReactionNotFoundException e) {
        log.error("Post reaction not found", e);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }
}
