package com.vanannek.socialmedia.comment.service;

import com.vanannek.socialmedia.comment.*;
import com.vanannek.socialmedia.post.Post;
import com.vanannek.socialmedia.post.service.PostService;
import com.vanannek.user.User;
import com.vanannek.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentMapper cMapper = CommentMapper.INSTANCE;

    private final CommentRepos commentRepos;
    private final PostService postService;
    private final UserService userService;

    @Override
    public CommentDTO save(CommentDTO commentDTO) {
        Comment comment = cMapper.toEntity(commentDTO);

        Long postId = commentDTO.getPostId();
        Post post = postService.getPostById(postId);
        comment.setPost(post);

        String username = commentDTO.getUsername();
        User user = userService.getUserByUsername(username);
        comment.setUser(user);

        Comment saved = commentRepos.save(comment);
        return cMapper.toDTO(saved);
    }

    @Override
    public CommentDTO update(UpdateCommentRequest updateRequest) {
        Long commentId = updateRequest.id();
        Comment comment = getById(commentId);

        comment.setContent( updateRequest.content() );
        comment.setUpdatedAt( updateRequest.updatedAt() );

        Comment saved = commentRepos.save(comment);
        return cMapper.toDTO(saved);
    }

    @Override
    public CommentDTO updateIsDeletedFlagById(Long commentId, boolean isDeleted) {
        Comment comment = getById(commentId);
        comment.setDeleted(isDeleted);

        Comment saved = commentRepos.save(comment);
        return cMapper.toDTO(saved);
    }

    @Override
    public List<CommentDTO> getComments(Long postId) {
        postService.getPostById(postId);

        List<Comment> comments = commentRepos.getComments(postId);
        return cMapper.toDTOs(comments);
    }

    @Override
    public Comment getById(Long commentId) {
        return commentRepos.findById(commentId)
                .orElseThrow(() -> new CommentNotFoundException("Could not find any comment with id=" + commentId));
    }
}
