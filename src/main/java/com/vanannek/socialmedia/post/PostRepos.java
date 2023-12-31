package com.vanannek.socialmedia.post;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepos extends JpaRepository<Post, Long> {
}
