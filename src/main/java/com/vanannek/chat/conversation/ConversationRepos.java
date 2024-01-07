package com.vanannek.chat.conversation;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ConversationRepos extends JpaRepository<Conversation, String> {

    @Query("SELECT COUNT(c) > 0 FROM Conversation c JOIN c.members m1 JOIN c.members m2 " +
            "WHERE c.type = 'PRIVATE' " +
            "AND (m1.member.username = :firstUsername AND m2.member.username = :secondUsername " +
            "OR m1.member.username = :secondUsername AND m2.member.username = :firstUsername)")
    boolean existsPrivateConversation(@Param("firstUsername") String firstUsername,
                                      @Param("secondUsername") String secondUsername);
}
