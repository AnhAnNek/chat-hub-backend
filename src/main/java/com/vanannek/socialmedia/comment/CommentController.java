package com.vanannek.socialmedia.comment;

import com.vanannek.socialmedia.comment.service.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
@Tag(name = "Comment")
public class CommentController {

    private static final Logger log = LogManager.getLogger(CommentController.class);

    @Autowired private CommentService commentService;

    @Operation(
            summary = "Add a comment to the post",
            description = "Add a comment to the post by providing the comment details.",
            responses = {
                    @ApiResponse(
                            description = "Created",
                            responseCode = "201"
                    ),
                    @ApiResponse(
                            description = "Unauthorized / Invalid Token",
                            responseCode = "401"
                    )
            }
    )
    @PostMapping("/add-comment")
    public ResponseEntity<String> addComment(@RequestBody CommentDTO commentDTO) {
        commentService.save(commentDTO);
        log.info(CommentUtils.COMMENT_ADDED_SUCCESSFULLY);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(CommentUtils.COMMENT_ADDED_SUCCESSFULLY);
    }

    @Operation(
            summary = "Update a comment to the post",
            description = "Update a comment to the post by providing the comment details.",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "Unauthorized / Invalid Token",
                            responseCode = "401"
                    )
            }
    )
    @PutMapping("/update-comment")
    public ResponseEntity<String> updateComment(@RequestBody UpdateCommentRequest updateRequest) {
        commentService.update(updateRequest);
        log.info(CommentUtils.COMMENT_UPDATED_SUCCESSFULLY);
        return ResponseEntity.ok(CommentUtils.COMMENT_UPDATED_SUCCESSFULLY);
    }

    @Operation(
            summary = "Delete a comment from the post",
            description = "Delete a comment from the post by providing the comment id.",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "Unauthorized / Invalid Token",
                            responseCode = "401"
                    )
            }
    )
    @DeleteMapping("/delete-comment/{commentId}")
    public ResponseEntity<String> deleteComment(@PathVariable("commentId") Long commentId) {
        commentService.updateIsDeletedFlagById(commentId, true);
        log.info(CommentUtils.POST_DELETED_SUCCESSFULLY);
        return ResponseEntity.ok(CommentUtils.POST_DELETED_SUCCESSFULLY);
    }

    @Operation(
            summary = "Retrieve comments",
            description = "Retrieve comments by providing the post id.",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "Unauthorized / Invalid Token",
                            responseCode = "401"
                    )
            }
    )
    @GetMapping("/get-comments/{postId}")
    public List<CommentDTO> getComments(@PathVariable Long postId) {
        List<CommentDTO> commentDTOs = commentService.getComments(postId);
        log.info(CommentUtils.COMMENTS_RETRIEVED_SUCCESSFULLY);
        return commentDTOs;
    }
}
