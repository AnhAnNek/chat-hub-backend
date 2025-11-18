package com.vanannek.socialmedia.postreaction.service;

import com.vanannek.socialmedia.UpdateReactionRequest;
import com.vanannek.socialmedia.postreaction.PostReaction;
import com.vanannek.socialmedia.postreaction.PostReactionDTO;

import java.util.List;

public interface PostReactionService {
    PostReactionDTO add(PostReactionDTO postReactionDTO);
    PostReactionDTO updateReactionType(UpdateReactionRequest updateRequest);
    PostReactionDTO updateIsDeletedFlagById(Long reactionId, boolean isDeleted);
    List<PostReactionDTO> getReactions(Long postId);
    PostReaction getReactionById(Long reactionId);
}
