package com.vanannek.socialmedia.post.service;

import com.vanannek.socialmedia.post.*;
import com.vanannek.user.User;
import com.vanannek.user.UserNotFoundException;
import com.vanannek.user.UserRepos;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepos postRepos;
    private final UserRepos userRepos;
    private final PostMapper pMapper = PostMapper.INSTANCE;

    @Override
    public PostDTO save(PostDTO postDTO) {
        Post post = pMapper.toEntity(postDTO);

        String username = postDTO.getUsername();
        User user = userRepos.findById(username)
                .orElseThrow(() -> new UserNotFoundException("Could not find any user with username=" + username));
        post.setUser(user);

        Post saved = postRepos.save(post);
        return pMapper.toDTO(saved);
    }

    @Override
    public PostDTO savePostWithReactionsAndComments(PostDTO postDTO) {
        Post post = pMapper.toEntity(postDTO);

        String ownerUsername = postDTO.getUsername();
        User owner = userRepos.findById(ownerUsername)
                .orElse(null);
        post.setUser(owner);

        post.getReactions().forEach(reaction -> {
            if (reaction == null) {
                return;
            }

            String username = reaction.getUsername();
            User user = userRepos.findById(username)
                    .orElse(null);
            reaction.setUser(user);
            reaction.setPost(post);
        });

        post.getComments().forEach(comment -> {
            String usernameForComment = comment.getUsername();
            User userForComment = userRepos.findById(usernameForComment)
                    .orElse(null);
            comment.setUser(userForComment);

            comment.setPost(post);
        });

        Post saved = postRepos.save(post);
        return pMapper.toDTO(saved);
    }

    @Override
    public PostDTO update(PostDTO postDTO) {
        try {
            Post.EStatus status = Post.EStatus.valueOf(postDTO.getStatus());

            Long postId = postDTO.getId();
            Post post = postRepos.findById(postId)
                    .orElseThrow(() -> new PostNotFoundException("Could not find any post with id=" + postId));

            post.setContent( postDTO.getContent() );
            post.setUpdatedAt( postDTO.getUpdatedAt() );
            post.setStatus( status );

            Post saved = postRepos.save(post);
            return pMapper.toDTO(saved);
        } catch (IllegalArgumentException e) {
            throw new PostStatusNotFoundException("Invalid post status: " + postDTO.getStatus());
        }
    }

    @Override
    public List<PostDTO> getNewsFeed(String username, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        List<Post> posts = postRepos.getNewsFeed(username, pageable);
        return pMapper.toDTOs(posts);
    }

    @Override
    public List<PostDTO> getMyPosts(String username, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        List<Post> posts = postRepos.getMyPosts(username, pageable);
        return pMapper.toDTOs(posts);
    }
}
