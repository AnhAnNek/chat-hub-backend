package com.vanannek.socialmedia.comment;

import com.vanannek.socialmedia.comment.service.CommentService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

    private static final Logger log = LogManager.getLogger(CommentController.class);

    @Autowired private CommentService commentService;

    @PostMapping("/add-comment")
    public ResponseEntity<String> addComment(@RequestBody CommentDTO commentDTO) {
        commentService.save(commentDTO);
        log.info(CommentUtils.COMMENT_ADDED_SUCCESSFULLY);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(CommentUtils.COMMENT_ADDED_SUCCESSFULLY);
    }

    @PutMapping("/update-comment")
    public ResponseEntity<String> updateComment(@RequestBody CommentDTO commentDTO) {
        commentService.update(commentDTO);
        log.info(CommentUtils.COMMENT_UPDATED_SUCCESSFULLY);
        return ResponseEntity.ok(CommentUtils.COMMENT_UPDATED_SUCCESSFULLY);
    }

    @DeleteMapping("/delete-comment/{commentId}")
    public ResponseEntity<String> deleteComment(@PathVariable("commentId") Long commentId) {
        commentService.updateIsDeletedFlagById(commentId, true);
        log.info(CommentUtils.POST_DELETED_SUCCESSFULLY);
        return ResponseEntity.ok(CommentUtils.POST_DELETED_SUCCESSFULLY);
    }

    @GetMapping("/get-comments/{postId}")
    public List<CommentDTO> getComments(@PathVariable Long postId) {
        List<CommentDTO> commentDTOs = commentService.getComments(postId);
        log.info(CommentUtils.COMMENTS_RETRIEVED_SUCCESSFULLY);
        return commentDTOs;
    }
}
