package com.vanannek.socialmedia.postreaction;

import com.vanannek.socialmedia.ReactionUtils;
import com.vanannek.socialmedia.postreaction.service.PostReactionService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/post-reactions")
public class PostReactionController {

    private static final Logger log = LogManager.getLogger(PostReactionController.class);

    @Autowired private PostReactionService postReactionService;

    @PostMapping("/add-reaction")
    public ResponseEntity<String> addReaction(@RequestBody PostReactionDTO postReactionDTO) {
        postReactionService.add(postReactionDTO);
        log.info(ReactionUtils.REACTION_ADDED_SUCCESSFULLY);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ReactionUtils.REACTION_ADDED_SUCCESSFULLY);
    }

    @PutMapping("/update-reaction")
    public ResponseEntity<String> updateReaction(@RequestBody PostReactionDTO postReactionDTO) {
        postReactionService.updateReactionType(
                postReactionDTO.getId(),
                postReactionDTO.getType()
        );
        log.info(ReactionUtils.REACTION_UPDATED_SUCCESSFULLY);
        return ResponseEntity.ok(ReactionUtils.REACTION_UPDATED_SUCCESSFULLY);
    }

    @DeleteMapping("/delete-reaction/{reactionId}")
    public ResponseEntity<String> deleteReaction(@PathVariable("reactionId") Long reactionId) {
        postReactionService.updateIsDeletedFlagById(reactionId, true);
        log.info(ReactionUtils.REACTION_DELETED_SUCCESSFULLY);
        return ResponseEntity.ok(ReactionUtils.REACTION_DELETED_SUCCESSFULLY);
    }

    @GetMapping("/get-reactions/{postId}")
    public List<PostReactionDTO> getReactions(@PathVariable("postId") Long postId) {
        List<PostReactionDTO> reactions = postReactionService.getReactions(postId);
        log.info(ReactionUtils.REACTIONS_RETRIEVED_SUCCESSFULLY);
        return reactions;
    }
}
