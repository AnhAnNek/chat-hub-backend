package com.vanannek.socialmedia.postreaction;

import jakarta.websocket.server.PathParam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostReactionRepos extends JpaRepository<PostReaction, Long> {

    @Query("SELECT COUNT(pr) > 0  FROM PostReaction pr " +
            "WHERE pr.user.username = :username AND pr.post.id = :postId AND pr.isDeleted = false")
    boolean existsByUsernameAndPostIdAndIsDeletedFalse(@Param("username") String username,
                                                       @Param("postId") Long postId);

    @Query("SELECT pr FROM PostReaction pr WHERE pr.post.id = :postId")
    List<PostReaction> getReactions(@PathParam("postId") Long postId);
}
