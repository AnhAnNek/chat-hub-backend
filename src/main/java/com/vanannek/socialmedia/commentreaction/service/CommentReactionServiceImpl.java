package com.vanannek.socialmedia.commentreaction.service;

import com.vanannek.socialmedia.EReactionType;
import com.vanannek.socialmedia.ReactionUtils;
import com.vanannek.socialmedia.UpdateReactionRequest;
import com.vanannek.socialmedia.comment.Comment;
import com.vanannek.socialmedia.comment.CommentMapper;
import com.vanannek.socialmedia.comment.service.CommentService;
import com.vanannek.socialmedia.commentreaction.*;
import com.vanannek.user.User;
import com.vanannek.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentReactionServiceImpl implements CommentReactionService {

    private final CommentMapper cMapper = CommentMapper.INSTANCE;

    private final CommentReactionRepos commentReactionRepos;
    private final UserService userService;
    private final CommentService commentService;

    @Override
    public CommentReactionDTO add(CommentReactionDTO commentReactionDTO) {
        String username = commentReactionDTO.getUsername();
        Long commentId = commentReactionDTO.getCommentId();
        if (commentReactionRepos.existsByUsernameAndCommentIdAndIsDeletedFalse(username, commentId)) {
            throw new DuplicateCommentReactionException("Found a duplicated comment reaction with id=" + commentId);
        }

        CommentReaction commentReaction = cMapper.toReactionEntity(commentReactionDTO);

        User user = userService.getUserByUsername(username);
        commentReaction.setUser(user);

        Comment comment = commentService.getById(commentId);
        commentReaction.setComment(comment);

        CommentReaction saved = commentReactionRepos.save(commentReaction);
        return cMapper.toReactionDTO(saved);
    }

    @Override
    public CommentReactionDTO updateReactionType(UpdateReactionRequest updateRequest) {
        CommentReaction commentReaction = getReactionById( updateRequest.id() );

        commentReaction.setType( updateRequest.type() );
        commentReaction.setDeleted(false);

        CommentReaction saved = commentReactionRepos.save(commentReaction);
        return cMapper.toReactionDTO(saved);
    }

    @Override
    public CommentReactionDTO updateIsDeletedFlagById(Long reactionId, boolean isDeleted) {
        CommentReaction commentReaction = getReactionById(reactionId);

        commentReaction.setDeleted(isDeleted);

        CommentReaction saved = commentReactionRepos.save(commentReaction);
        return cMapper.toReactionDTO(saved);
    }

    private CommentReaction getReactionById(Long reactionId) {
        return commentReactionRepos.findById(reactionId)
                .orElseThrow(() -> new CommentReactionNotFoundException("Could not find any comment reaction with id=" + reactionId));
    }

    @Override
    public List<CommentReactionDTO> getReactions(Long commentId) {
        commentService.getById(commentId);

        List<CommentReaction> reactions = commentReactionRepos.getReactions(commentId);
        return cMapper.toReactionDTOs(reactions);
    }
}
