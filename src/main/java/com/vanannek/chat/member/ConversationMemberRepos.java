package com.vanannek.chat.member;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ConversationMemberRepos extends JpaRepository<ConversationMember, Long> {
    @Query("SELECT cm.conversation.id FROM ConversationMember cm WHERE cm.member.username = :username")
    List<String> getConversationIdsByUsername(@Param("username") String username);

    @Modifying
    @Query("DELETE FROM ConversationMember cm " +
            "WHERE cm.conversation.id = :conversationId AND cm.member.username = :username")
    ConversationMember deleteByUsername(@Param("conversationId") String conversationId,
                                        @Param("username") String username);

    @Query("SELECT cm FROM ConversationMember cm WHERE cm.conversation.id = :conversationId AND cm.member.username = :username")
    Optional<ConversationMember> findByConversationIdAndUsername(@Param("conversationId") String conversationId,
                                                                 @Param("username") String username);
}