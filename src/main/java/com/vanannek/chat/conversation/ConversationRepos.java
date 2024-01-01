package com.vanannek.chat.conversation;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConversationRepos extends JpaRepository<Conversation, String> {
}
