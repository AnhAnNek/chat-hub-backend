package com.vanannek.socialmedia.commentreaction;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentReactionRepos extends JpaRepository<CommentReaction, Long> {

    @Query("SELECT COUNT(cr) > 0  FROM CommentReaction cr " +
            "WHERE cr.user.username = :username AND cr.comment.id = :commentId AND cr.isDeleted = false")
    boolean existsByUsernameAndCommentIdAndIsDeletedFalse(@Param("username") String username,
                                                          @Param("commentId") Long commentId);

    @Query("SELECT cr FROM CommentReaction cr WHERE cr.comment.id = :commentId AND cr.isDeleted = false")
    List<CommentReaction> getReactions(@Param("commentId") Long commentId);
}
