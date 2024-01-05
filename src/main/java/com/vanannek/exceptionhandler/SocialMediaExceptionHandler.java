package com.vanannek.exceptionhandler;

import com.vanannek.socialmedia.comment.CommentNotFoundException;
import com.vanannek.socialmedia.commentreaction.CommentReactionNotFoundException;
import com.vanannek.socialmedia.commentreaction.DuplicateCommentReactionException;
import com.vanannek.socialmedia.post.PostNotFoundException;
import com.vanannek.socialmedia.post.PostStatusNotFoundException;
import com.vanannek.socialmedia.postreaction.DuplicatePostReactionException;
import com.vanannek.socialmedia.postreaction.PostReactionNotFoundException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class SocialMediaExceptionHandler {

    private static final Logger log = LogManager.getLogger(SocialMediaExceptionHandler.class);

    @ExceptionHandler(CommentNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleCommentNotFound(CommentNotFoundException e) {
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.NOT_FOUND, e.getMessage(), LocalDateTime.now());
        log.error("Comment not found", e);
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(errorResponse);
    }

    @ExceptionHandler(CommentReactionNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleCommentNotFound(CommentReactionNotFoundException e) {
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.NOT_FOUND, e.getMessage(), LocalDateTime.now());
        log.error("Comment reaction not found", e);
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(errorResponse);
    }

    @ExceptionHandler(DuplicateCommentReactionException.class)
    public ResponseEntity<ErrorResponse> handleDuplicatePostReaction(DuplicateCommentReactionException e) {
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.CONFLICT, e.getMessage(), LocalDateTime.now());
        log.error("Duplicate comment reaction", e);
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(errorResponse);
    }

    @ExceptionHandler(PostNotFoundException.class)
    public ResponseEntity<ErrorResponse> handlePostNotFound(PostNotFoundException e) {
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.NOT_FOUND, e.getMessage(), LocalDateTime.now());
        log.error("Post not found", e);
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(errorResponse);
    }

    @ExceptionHandler(PostStatusNotFoundException.class)
    public ResponseEntity<ErrorResponse> handlePostNotFound(PostStatusNotFoundException e) {
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.NOT_FOUND, e.getMessage(), LocalDateTime.now());
        log.error("Post status not found", e);
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(errorResponse);
    }

    @ExceptionHandler(DuplicatePostReactionException.class)
    public ResponseEntity<ErrorResponse> handleDuplicatePostReaction(DuplicatePostReactionException e) {
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.CONFLICT, e.getMessage(), LocalDateTime.now());
        log.error("Duplicate post reaction", e);
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(errorResponse);
    }

    @ExceptionHandler(PostReactionNotFoundException.class)
    public ResponseEntity<ErrorResponse> handlePostReactionNotFound(PostReactionNotFoundException e) {
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.NOT_FOUND, e.getMessage(), LocalDateTime.now());
        log.error("Post reaction not found", e);
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(errorResponse);
    }
}
