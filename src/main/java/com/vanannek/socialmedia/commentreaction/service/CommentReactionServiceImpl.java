package com.vanannek.socialmedia.commentreaction.service;

import com.vanannek.socialmedia.EReactionType;
import com.vanannek.socialmedia.ReactionUtils;
import com.vanannek.socialmedia.comment.Comment;
import com.vanannek.socialmedia.comment.CommentMapper;
import com.vanannek.socialmedia.comment.CommentNotFoundException;
import com.vanannek.socialmedia.comment.CommentRepos;
import com.vanannek.socialmedia.commentreaction.*;
import com.vanannek.user.User;
import com.vanannek.user.UserNotFoundException;
import com.vanannek.user.UserRepos;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentReactionServiceImpl implements CommentReactionService {

    private final CommentReactionRepos commentReactionRepos;
    private final UserRepos userRepos;
    private final CommentRepos commentRepos;
    private final CommentMapper cMapper = CommentMapper.INSTANCE;
    
    @Override
    public CommentReactionDTO add(CommentReactionDTO commentReactionDTO) {
        String username = commentReactionDTO.getUsername();
        Long commentId = commentReactionDTO.getCommentId();
        if (commentReactionRepos.existsByUsernameAndCommentIdAndIsDeletedFalse(username, commentId)) {
            throw new DuplicateCommentReactionException("Found a duplicated comment reaction with id=" + commentId);
        }

        CommentReaction commentReaction = cMapper.toReactionEntity(commentReactionDTO);

        User user = userRepos.findById(username)
                .orElseThrow(() -> new UserNotFoundException("Could not find any user with username=" + username));
        commentReaction.setUser(user);

        Comment comment = commentRepos.findById(commentId)
                .orElseThrow(() -> new CommentNotFoundException("Could not find any comment with id=" + commentId));
        commentReaction.setComment(comment);

        CommentReaction saved = commentReactionRepos.save(commentReaction);
        return cMapper.toReactionDTO(saved);
    }

    @Override
    public CommentReactionDTO updateReactionType(Long reactionId, String type) {
        EReactionType reactionType = ReactionUtils.toEReactionType(type);

        CommentReaction commentReaction = commentReactionRepos.findById(reactionId)
                .orElseThrow(() -> new CommentReactionNotFoundException("Could not find any comment reaction with id=" + reactionId));

        commentReaction.setType(reactionType);
        commentReaction.setDeleted(false);

        CommentReaction saved = commentReactionRepos.save(commentReaction);
        return cMapper.toReactionDTO(saved);
    }

    @Override
    public CommentReactionDTO updateIsDeletedFlagById(Long reactionId, boolean isDeleted) {
        CommentReaction commentReaction = commentReactionRepos.findById(reactionId)
                .orElseThrow(() -> new CommentReactionNotFoundException("Could not find any comment reaction with id=" + reactionId));

        commentReaction.setDeleted(isDeleted);

        CommentReaction saved = commentReactionRepos.save(commentReaction);
        return cMapper.toReactionDTO(saved);
    }

    @Override
    public List<CommentReactionDTO> getReactions(Long commentId) {
        if (!commentRepos.existsById(commentId)) {
            throw new CommentNotFoundException("Could not find any comment reaction with commentId=" + commentId);
        }

        List<CommentReaction> reactions = commentReactionRepos.getReactions(commentId);
        return cMapper.toReactionDTOs(reactions);
    }
}
