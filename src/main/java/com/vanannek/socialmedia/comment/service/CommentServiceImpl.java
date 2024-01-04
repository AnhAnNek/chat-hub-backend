package com.vanannek.socialmedia.comment.service;

import com.vanannek.socialmedia.comment.*;
import com.vanannek.socialmedia.post.Post;
import com.vanannek.socialmedia.post.PostNotFoundException;
import com.vanannek.socialmedia.post.PostRepos;
import com.vanannek.user.User;
import com.vanannek.user.UserNotFoundException;
import com.vanannek.user.UserRepos;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.awt.print.Pageable;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepos commentRepos;
    private final PostRepos postRepos;
    private final UserRepos userRepos;

    private final CommentMapper cMapper = CommentMapper.INSTANCE;

    @Override
    public CommentDTO save(CommentDTO commentDTO) {
        Comment comment = cMapper.toEntity(commentDTO);

        Long postId = commentDTO.getPostId();
        Post post = postRepos.findById(postId)
                .orElseThrow(() -> new PostNotFoundException("Could not find any post with id=" + postId));
        comment.setPost(post);

        String username = commentDTO.getUsername();
        User user = userRepos.findById(username)
                .orElseThrow(() -> new UserNotFoundException("Could not find any user with username=" + username));
        comment.setUser(user);

        Comment saved = commentRepos.save(comment);
        return cMapper.toDTO(saved);
    }

    @Override
    public CommentDTO update(CommentDTO commentDTO) {
        Long commentId = commentDTO.getId();
        Comment comment = commentRepos.findById(commentId)
                .orElseThrow(() -> new CommentNotFoundException("Could not find any comment with id=" + commentId));

        comment.setContent( commentDTO.getContent() );
        comment.setUpdatedAt( commentDTO.getUpdatedAt() );

        Comment saved = commentRepos.save(comment);
        return cMapper.toDTO(saved);
    }

    @Override
    public CommentDTO updateIsDeletedFlagById(Long commentId, boolean isDeleted) {
        Comment comment = commentRepos.findById(commentId)
                .orElseThrow(() -> new CommentNotFoundException("Could not find any comment with id=" + commentId));

        comment.setDeleted(isDeleted);

        Comment saved = commentRepos.save(comment);
        return cMapper.toDTO(saved);
    }

    @Override
    public List<CommentDTO> getComments(Long postId) {
        List<Comment> comments = commentRepos.getComments(postId);
        return cMapper.toDTOs(comments);
    }
}
