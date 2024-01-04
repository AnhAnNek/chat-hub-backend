package com.vanannek.socialmedia.comment.service;

import com.vanannek.socialmedia.comment.CommentDTO;

import java.util.List;

public interface CommentService {
    CommentDTO save(CommentDTO commentDTO);
    CommentDTO update(CommentDTO commentDTO);
    CommentDTO updateIsDeletedFlagById(Long commentId, boolean isDeleted);
    List<CommentDTO> getComments(Long postId);
}
