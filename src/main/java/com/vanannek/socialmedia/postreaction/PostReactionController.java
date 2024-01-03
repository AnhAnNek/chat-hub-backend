package com.vanannek.socialmedia.postreaction;

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
        log.info(PostReactionUtils.ADD_REACTION_SUCCESSFULLY);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(PostReactionUtils.ADD_REACTION_SUCCESSFULLY);
    }

    @PutMapping("/update-reaction")
    public ResponseEntity<String> updateReaction(@RequestBody PostReactionDTO postReactionDTO) {
        postReactionService.updateReactionType(
                postReactionDTO.getId(),
                postReactionDTO.getType()
        );
        return ResponseEntity.ok("Update post reaction successfully");
    }

    @DeleteMapping("/delete-reaction/{reactionId}")
    public ResponseEntity<String> deleteReaction(@PathVariable("reactionId") Long reactionId) {
        postReactionService.updateIsDeletedFlagById(reactionId, true);
        log.info(PostReactionUtils.DELETE_REACTION_SUCCESSFULLY);
        return ResponseEntity.ok(PostReactionUtils.DELETE_REACTION_SUCCESSFULLY);
    }

    @GetMapping("/get-reactions/{postId}")
    public List<PostReactionDTO> getReactions(@PathVariable("postId") Long postId) {
        List<PostReactionDTO> reactions = postReactionService.getReactions(postId);
        log.info(PostReactionUtils.RETRIEVED_REACTIONS_FOR_POST);
        return reactions;
    }
}
