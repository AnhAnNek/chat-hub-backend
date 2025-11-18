package com.vanannek.socialmedia.commentreaction.service;

import com.vanannek.socialmedia.UpdateReactionRequest;
import com.vanannek.socialmedia.commentreaction.CommentReactionDTO;

import java.util.List;

public interface CommentReactionService {
    CommentReactionDTO add(CommentReactionDTO commentReactionDTO);
    CommentReactionDTO updateReactionType(UpdateReactionRequest updateRequest);
    CommentReactionDTO updateIsDeletedFlagById(Long reactionId, boolean isDeleted);
    List<CommentReactionDTO> getReactions(Long commentId);
}
