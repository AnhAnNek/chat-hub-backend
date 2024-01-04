package com.vanannek.socialmedia.commentreaction;

import com.vanannek.socialmedia.ReactionUtils;
import com.vanannek.socialmedia.commentreaction.service.CommentReactionService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comment-reactions")
public class CommentReactionController {

    private static final Logger log = LogManager.getLogger(CommentReactionController.class);

    @Autowired private CommentReactionService commentReactionService;

    @PostMapping("/add-reaction")
    public ResponseEntity<String> addReaction(@RequestBody CommentReactionDTO CommentReactionDTO) {
        commentReactionService.add(CommentReactionDTO);
        log.info(ReactionUtils.REACTION_ADDED_SUCCESSFULLY);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ReactionUtils.REACTION_ADDED_SUCCESSFULLY);
    }

    @PutMapping("/update-reaction")
    public ResponseEntity<String> updateReaction(@RequestBody CommentReactionDTO CommentReactionDTO) {
        commentReactionService.updateReactionType(
                CommentReactionDTO.getId(),
                CommentReactionDTO.getType()
        );
        log.info(ReactionUtils.REACTION_UPDATED_SUCCESSFULLY);
        return ResponseEntity.ok(ReactionUtils.REACTION_UPDATED_SUCCESSFULLY);
    }

    @DeleteMapping("/delete-reaction/{reactionId}")
    public ResponseEntity<String> deleteReaction(@PathVariable("reactionId") Long reactionId) {
        commentReactionService.updateIsDeletedFlagById(reactionId, true);
        log.info(ReactionUtils.REACTION_DELETED_SUCCESSFULLY);
        return ResponseEntity.ok(ReactionUtils.REACTION_DELETED_SUCCESSFULLY);
    }

    @GetMapping("/get-reactions/{commentId}")
    public List<CommentReactionDTO> getReactions(@PathVariable("commentId") Long commentId) {
        List<CommentReactionDTO> reactions = commentReactionService.getReactions(commentId);
        log.info(ReactionUtils.REACTIONS_RETRIEVED_SUCCESSFULLY);
        return reactions;
    }
}
