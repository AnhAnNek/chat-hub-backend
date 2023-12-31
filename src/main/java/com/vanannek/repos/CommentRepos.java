package com.vanannek.repos;

import com.vanannek.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepos extends JpaRepository<Comment, Long> {
}
