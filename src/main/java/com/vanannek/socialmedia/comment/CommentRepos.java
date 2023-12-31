package com.vanannek.socialmedia.comment;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepos extends JpaRepository<Comment, Long> {
}
