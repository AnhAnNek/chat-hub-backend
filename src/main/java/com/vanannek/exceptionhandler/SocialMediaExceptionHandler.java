package com.vanannek.exceptionhandler;

import com.vanannek.socialmedia.comment.CommentNotFoundException;
import com.vanannek.socialmedia.commentreaction.CommentReactionNotFoundException;
import com.vanannek.socialmedia.commentreaction.DuplicateCommentReactionException;
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

    @ExceptionHandler(CommentNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<String> handleCommentNotFound(CommentNotFoundException e) {
        log.error("Comment not found", e);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

    @ExceptionHandler(CommentReactionNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<String> handleCommentNotFound(CommentReactionNotFoundException e) {
        log.error("Comment reaction not found", e);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

    @ExceptionHandler(DuplicateCommentReactionException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<String> handleDuplicatePostReaction(DuplicateCommentReactionException e) {
        log.error("Duplicate comment reaction", e);
        return ResponseEntity.badRequest().body(e.getMessage());
    }

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
