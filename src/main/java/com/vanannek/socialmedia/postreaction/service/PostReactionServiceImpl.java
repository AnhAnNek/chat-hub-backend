package com.vanannek.socialmedia.postreaction.service;

import com.vanannek.socialmedia.EReactionType;
import com.vanannek.socialmedia.UnsupportedReactionTypeException;
import com.vanannek.socialmedia.post.*;
import com.vanannek.socialmedia.postreaction.*;
import com.vanannek.user.User;
import com.vanannek.user.UserNotFoundException;
import com.vanannek.user.UserRepos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostReactionServiceImpl implements PostReactionService {

    @Autowired private PostReactionRepos postReactionRepos;
    @Autowired private UserRepos userRepos;
    @Autowired private PostRepos postRepos;
    private final PostMapper pMapper = PostMapper.INSTANCE;

    @Override
    public PostReactionDTO add(PostReactionDTO postReactionDTO) {
        String username = postReactionDTO.getUsername();
        Long postId = postReactionDTO.getPostId();
        if (postReactionRepos.existsByUsernameAndPostIdAndIsDeletedFalse(username, postId)) {
            throw new DuplicatePostReactionException("Found a duplicated post reaction with id=" + postReactionDTO.getPostId());
        }

        PostReaction postReaction = pMapper.toReactionEntity(postReactionDTO);

        User user = userRepos.findById(username)
                .orElseThrow(() -> new UserNotFoundException("Could not find any user with username=" + username));
        postReaction.setUser(user);

        Post post = postRepos.findById(postId)
                .orElseThrow(() -> new PostNotFoundException("Could not find any post with id=" + postId));
        postReaction.setPost(post);

        PostReaction saved = postReactionRepos.save(postReaction);
        return pMapper.toReactionDTO(saved);
    }

    @Override
    public PostReactionDTO updateReactionType(Long reactionId, String type) {
        try {
            EReactionType reactionType = EReactionType.valueOf(type.toUpperCase());

            PostReaction postReaction = postReactionRepos.findById(reactionId)
                    .orElseThrow(() -> new PostReactionNotFoundException("Could not find any post reaction with id=" + reactionId));

            postReaction.setType(reactionType);
            postReaction.setDeleted(false);

            PostReaction saved = postReactionRepos.save(postReaction);
            return pMapper.toReactionDTO(saved);
        } catch (IllegalArgumentException e) {
            throw new UnsupportedReactionTypeException("Unsupported reaction type: " + type);
        }
    }

    @Override
    public PostReactionDTO updateIsDeletedFlagById(Long reactionId, boolean isDeleted) {
        PostReaction postReaction = postReactionRepos.findById(reactionId)
                .orElseThrow(() -> new PostReactionNotFoundException("Could not find any post reaction with id=" + reactionId));

        postReaction.setDeleted(isDeleted);

        PostReaction saved = postReactionRepos.save(postReaction);
        return pMapper.toReactionDTO(saved);
    }

    @Override
    public List<PostReactionDTO> getReactions(Long postId) {
        List<PostReaction> reactions = postReactionRepos.getReactions(postId);
        return pMapper.toReactionDTOs(reactions);
    }
}
