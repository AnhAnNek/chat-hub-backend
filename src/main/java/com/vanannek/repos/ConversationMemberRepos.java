package com.vanannek.repos;

import com.vanannek.entity.ConversationMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ConversationMemberRepos extends JpaRepository<ConversationMember, Long> {
    @Query("SELECT cm.conversation.id FROM ConversationMember cm WHERE cm.member.username = :username")
    List<String> getConversationIdsByUsername(@Param("username") String username);

    @Modifying
    @Query("DELETE FROM ConversationMember cm " +
            "WHERE cm.conversation.id = :conversationId AND cm.member.username = :username")
    ConversationMember deleteByUsername(String conversationId, String username);
}
