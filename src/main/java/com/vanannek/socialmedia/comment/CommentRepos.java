package com.vanannek.socialmedia.comment;

import com.vanannek.socialmedia.comment.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepos extends JpaRepository<Comment, Long> {
}
