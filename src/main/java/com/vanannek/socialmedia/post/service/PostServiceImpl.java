package com.vanannek.socialmedia.post.service;

import com.vanannek.socialmedia.post.*;
import com.vanannek.user.User;
import com.vanannek.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostMapper pMapper = PostMapper.INSTANCE;

    private final PostRepos postRepos;
    private final UserService userService;

    @Override
    public PostDTO save(PostDTO postDTO) {
        Post post = pMapper.toEntity(postDTO);

        String username = postDTO.getUsername();
        User user = userService.getUserByUsername(username);
        post.setUser(user);

        Post saved = postRepos.save(post);
        return pMapper.toDTO(saved);
    }

    @Override
    public PostDTO savePostWithReactionsAndComments(PostDTO postDTO) {
        Post post = pMapper.toEntity(postDTO);

        String ownerUsername = postDTO.getUsername();
        User owner = userService.getUserByUsername(ownerUsername);
        post.setUser(owner);

        post.getReactions().forEach(reaction -> {
            if (reaction == null) {
                return;
            }

            String username = reaction.getUsername();
            User user = userService.getUserByUsername(username);
            reaction.setUser(user);
            reaction.setPost(post);
        });

        post.getComments().forEach(comment -> {
            String usernameForComment = comment.getUsername();
            User userForComment = userService.getUserByUsername(usernameForComment);
            comment.setUser(userForComment);

            comment.setPost(post);
        });

        Post saved = postRepos.save(post);
        return pMapper.toDTO(saved);
    }

    @Override
    public PostDTO update(PostDTO postDTO) {
        String statusStr = postDTO.getStatus();
        Post.EStatus status = PostUtils.toEPostStatus(statusStr);

        Long postId = postDTO.getId();
        Post post = getPostById(postId);

        post.setContent( postDTO.getContent() );
        post.setUpdatedAt( postDTO.getUpdatedAt() );
        post.setStatus( status );

        Post saved = postRepos.save(post);
        return pMapper.toDTO(saved);
    }

    @Override
    public Post getPostById(Long postId) {
        return postRepos.findById(postId)
                .orElseThrow(() -> new PostNotFoundException("Could not find any post with id=" + postId));
    }

    @Override
    public List<PostDTO> getNewsFeed(String username, int page, int size) {
        userService.getUserByUsername(username);

        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        List<Post> posts = postRepos.getNewsFeed(username, pageable);
        return pMapper.toDTOs(posts);
    }

    @Override
    public List<PostDTO> getMyPosts(String username, int page, int size) {
        userService.getUserByUsername(username);

        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        List<Post> posts = postRepos.getMyPosts(username, pageable);
        return pMapper.toDTOs(posts);
    }
}
