package com.vanannek.socialmedia.postreaction.service;

import com.vanannek.socialmedia.EReactionType;
import com.vanannek.socialmedia.ReactionUtils;
import com.vanannek.socialmedia.UpdateReactionRequest;
import com.vanannek.socialmedia.post.Post;
import com.vanannek.socialmedia.post.PostMapper;
import com.vanannek.socialmedia.post.service.PostService;
import com.vanannek.socialmedia.postreaction.*;
import com.vanannek.user.User;
import com.vanannek.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostReactionServiceImpl implements PostReactionService {

    private final PostMapper pMapper = PostMapper.INSTANCE;

    private final PostReactionRepos postReactionRepos;
    private final UserService userService;
    private final PostService postService;

    @Override
    public PostReactionDTO add(PostReactionDTO postReactionDTO) {
        String username = postReactionDTO.getUsername();
        Long postId = postReactionDTO.getPostId();
        if (postReactionRepos.existsByUsernameAndPostIdAndIsDeletedFalse(username, postId)) {
            throw new DuplicatePostReactionException("Found a duplicated post reaction with id=" + postReactionDTO.getPostId());
        }

        PostReaction postReaction = pMapper.toReactionEntity(postReactionDTO);

        User user = userService.getUserByUsername(username);
        postReaction.setUser(user);

        Post post = postService.getPostById(postId);
        postReaction.setPost(post);

        PostReaction saved = postReactionRepos.save(postReaction);
        return pMapper.toReactionDTO(saved);
    }

    @Override
    public PostReactionDTO updateReactionType(UpdateReactionRequest updateRequest) {
        PostReaction postReaction = getReactionById( updateRequest.id() );

        postReaction.setType( updateRequest.type() );
        postReaction.setDeleted(false);

        PostReaction saved = postReactionRepos.save(postReaction);
        return pMapper.toReactionDTO(saved);
    }

    @Override
    public PostReactionDTO updateIsDeletedFlagById(Long reactionId, boolean isDeleted) {
        PostReaction postReaction = getReactionById(reactionId);

        postReaction.setDeleted(isDeleted);

        PostReaction saved = postReactionRepos.save(postReaction);
        return pMapper.toReactionDTO(saved);
    }

    @Override
    public List<PostReactionDTO> getReactions(Long postId) {
        postService.getPostById(postId);

        List<PostReaction> reactions = postReactionRepos.getReactions(postId);
        return pMapper.toReactionDTOs(reactions);
    }

    @Override
    public PostReaction getReactionById(Long reactionId) {
        return postReactionRepos.findById(reactionId)
                .orElseThrow(() -> new PostReactionNotFoundException("Could not find any post reaction with id=" + reactionId));
    }
}
