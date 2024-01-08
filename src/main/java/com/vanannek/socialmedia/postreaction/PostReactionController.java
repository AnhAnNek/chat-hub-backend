package com.vanannek.socialmedia.postreaction;

import com.vanannek.socialmedia.ReactionUtils;
import com.vanannek.socialmedia.UpdateReactionRequest;
import com.vanannek.socialmedia.postreaction.service.PostReactionService;
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
@RequestMapping("/api/post-reactions")
@Tag(name = "Post Reaction")
public class PostReactionController {

    private static final Logger log = LogManager.getLogger(PostReactionController.class);

    @Autowired private PostReactionService postReactionService;

    @Operation(
            summary = "Add Post Reaction",
            description = "Add a reaction to a post by providing the necessary details in the request body.",
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
    public ResponseEntity<String> addReaction(@RequestBody PostReactionDTO postReactionDTO) {
        postReactionService.add(postReactionDTO);
        log.info(ReactionUtils.REACTION_ADDED_SUCCESSFULLY);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ReactionUtils.REACTION_ADDED_SUCCESSFULLY);
    }

    @Operation(
            summary = "Update Post Reaction",
            description = "Update the type of reaction to a post by providing the necessary details in the request body.",
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
        postReactionService.updateReactionType(updateRequest);
        log.info(ReactionUtils.REACTION_UPDATED_SUCCESSFULLY);
        return ResponseEntity.ok(ReactionUtils.REACTION_UPDATED_SUCCESSFULLY);
    }

    @Operation(
            summary = "Delete Post Reaction",
            description = "Delete a reaction from a post by providing the reaction id.",
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
        postReactionService.updateIsDeletedFlagById(reactionId, true);
        log.info(ReactionUtils.REACTION_DELETED_SUCCESSFULLY);
        return ResponseEntity.ok(ReactionUtils.REACTION_DELETED_SUCCESSFULLY);
    }

    @Operation(
            summary = "Retrieve Post Reactions",
            description = "Retrieve reactions associated with a specific post by providing the post id.",
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
    @GetMapping("/get-reactions/{postId}")
    public List<PostReactionDTO> getReactions(@PathVariable("postId") Long postId) {
        List<PostReactionDTO> reactions = postReactionService.getReactions(postId);
        log.info(ReactionUtils.REACTIONS_RETRIEVED_SUCCESSFULLY);
        return reactions;
    }
}
