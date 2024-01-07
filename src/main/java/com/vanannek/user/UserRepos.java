package com.vanannek.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepos extends JpaRepository<User, String> {
    @Query("SELECT u.gender FROM User u WHERE u.username = :username")
    User.EGender getGenderByUsername(@Param("username") String username);

    @Query(value = "SELECT * FROM users u WHERE u.username <> :username AND u.username NOT IN " +
            "(SELECT DISTINCT cm.member_username FROM conversation_members cm " +
            "WHERE cm.conversation_id IN (SELECT c.id FROM conversations c WHERE c.type = 'PRIVATE'))", nativeQuery = true)
    List<User> getUnchattedUsers(@Param("username") String curUsername);
}
