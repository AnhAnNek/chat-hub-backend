package com.vanannek.repos;

import com.vanannek.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepos extends JpaRepository<Post, Long> {
}
