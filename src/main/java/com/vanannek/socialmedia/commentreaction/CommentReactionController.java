package com.vanannek.socialmedia.commentreaction;

import com.vanannek.socialmedia.ReactionUtils;
import com.vanannek.socialmedia.UpdateReactionRequest;
import com.vanannek.socialmedia.commentreaction.service.CommentReactionService;
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
@RequestMapping("/api/comment-reactions")
@Tag(name = "Comment Reaction")
public class CommentReactionController {

    private static final Logger log = LogManager.getLogger(CommentReactionController.class);

    @Autowired private CommentReactionService commentReactionService;

    @Operation(
            summary = "Add a reaction to the comment",
            description = "Add a reaction to the comment by providing the comment reaction details.",
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
    @PostMapping("/add-reaction")
    public ResponseEntity<String> addReaction(@RequestBody CommentReactionDTO CommentReactionDTO) {
        commentReactionService.add(CommentReactionDTO);
        log.info(ReactionUtils.REACTION_ADDED_SUCCESSFULLY);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ReactionUtils.REACTION_ADDED_SUCCESSFULLY);
    }

    @Operation(
            summary = "Update a reaction to the comment",
            description = "Update a reaction to the comment by providing the comment reaction details.",
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
    @PutMapping("/update-reaction")
    public ResponseEntity<String> updateReaction(@RequestBody UpdateReactionRequest updateRequest) {
        commentReactionService.updateReactionType(updateRequest);
        log.info(ReactionUtils.REACTION_UPDATED_SUCCESSFULLY);
        return ResponseEntity.ok(ReactionUtils.REACTION_UPDATED_SUCCESSFULLY);
    }

    @Operation(
            summary = "Delete comment reaction",
            description = "Delete a reaction from the comment by providing the reaction id.",
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
    @DeleteMapping("/delete-reaction/{reactionId}")
    public ResponseEntity<String> deleteReaction(@PathVariable("reactionId") Long reactionId) {
        commentReactionService.updateIsDeletedFlagById(reactionId, true);
        log.info(ReactionUtils.REACTION_DELETED_SUCCESSFULLY);
        return ResponseEntity.ok(ReactionUtils.REACTION_DELETED_SUCCESSFULLY);
    }

    @Operation(
            summary = "Retrieve comment reactions",
            description = "Retrieve reactions associated with a specific comment by providing the comment id.",
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
    @GetMapping("/get-reactions/{commentId}")
    public ResponseEntity<List<CommentReactionDTO>> getReactions(@PathVariable("commentId") Long commentId) {
        List<CommentReactionDTO> reactions = commentReactionService.getReactions(commentId);
        log.info(ReactionUtils.REACTIONS_RETRIEVED_SUCCESSFULLY);
        return ResponseEntity.ok(reactions);
    }
}
