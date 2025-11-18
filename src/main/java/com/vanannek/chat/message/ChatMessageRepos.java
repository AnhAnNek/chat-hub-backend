package com.vanannek.chat.message;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatMessageRepos extends JpaRepository<ChatMessage, Long> {
    @Query("SELECT cm FROM ChatMessage cm " +
            "WHERE cm.conversation.id = :conversationId " +
            "ORDER BY cm.sendingTime")
    List<ChatMessage> getMessages(@Param("conversationId") String conversationId);
}
