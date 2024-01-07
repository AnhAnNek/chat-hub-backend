package com.vanannek.socialmedia.post;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepos extends JpaRepository<Post, Long> {

    @Query(value = "SELECT p FROM  Post p WHERE p.user.username NOT LIKE :username " +
            "AND p.status != 'DELETED' AND p.status != 'HIDDEN'")
    List<Post> getNewsFeed(@Param("username") String username, Pageable pageable);

    @Query(value = "SELECT p FROM  Post p WHERE p.user.username = :username AND p.status != 'DELETED'")
    List<Post> getMyPosts(@Param("username") String username, Pageable pageable);
}
