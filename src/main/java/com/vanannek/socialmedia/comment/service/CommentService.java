package com.vanannek.socialmedia.comment.service;

import com.vanannek.socialmedia.comment.Comment;
import com.vanannek.socialmedia.comment.CommentDTO;
import com.vanannek.socialmedia.comment.UpdateCommentRequest;

import java.util.List;

public interface CommentService {
    CommentDTO save(CommentDTO commentDTO);
    CommentDTO update(UpdateCommentRequest updateRequest);
    CommentDTO updateIsDeletedFlagById(Long commentId, boolean isDeleted);
    List<CommentDTO> getComments(Long postId);
    Comment getById(Long commentId);
}
