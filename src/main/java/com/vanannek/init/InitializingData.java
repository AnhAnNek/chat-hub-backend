package com.vanannek.init;

import com.vanannek.dto.ChatMessageDTO;
import com.vanannek.dto.ConversationDTO;
import com.vanannek.dto.ConversationMemberDTO;
import com.vanannek.dto.UserDTO;
import com.vanannek.entity.ChatMessage;
import com.vanannek.entity.Conversation;
import com.vanannek.entity.ConversationMember;
import com.vanannek.entity.User;
import com.vanannek.service.conversation.ConversationService;
import com.vanannek.service.user.UserService;
import com.vanannek.util.ConversationUtils;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class InitializingData implements CommandLineRunner {

    private final Logger log = LogManager.getLogger(InitializingData.class);

    private final UserService userService;
    private final ConversationService conversationService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        deleteAll();
        initData();
    }

    private void deleteAll() {
        conversationService.deleteAll();
        userService.deleteAll();
    }

    private void initData() {
        List<UserDTO> userDTOs = initUsers();
        ConversationDTO savedPrivateConversationDTO = initPrivateConversation(userDTOs.get(0), userDTOs.get(1));
        ConversationDTO savedPrivateConversationDTO1 = initPrivateConversation(userDTOs.get(2), userDTOs.get(0));
        ConversationDTO savedPrivateConversationDTO3 = initPrivateConversation(userDTOs.get(0), userDTOs.get(3));
        ConversationDTO savedGroup = initGroup(userDTOs);
    }

    private List<UserDTO> initUsers() {
        final String DEFAULT_HASHED_PASS = passwordEncoder
                .encode("123456");

        List<UserDTO> userDTOs = new ArrayList<>();
        userDTOs.add(UserDTO.builder()
                .username("vanannek")
                .passHash(DEFAULT_HASHED_PASS)
                .fullName("Trần Văn An")
                .email("vanantran99@gmail.com")
                .gender(User.EGender.MALE)
                .build());

        userDTOs.add(UserDTO.builder()
                .username("hoanglong")
                .passHash(DEFAULT_HASHED_PASS)
                .fullName("Hoàng Long")
                .email("user2@gmail.com")
                .gender(User.EGender.FEMALE)
                .build());

        userDTOs.add(UserDTO.builder()
                .username("thanhlong")
                .passHash(DEFAULT_HASHED_PASS)
                .fullName("Thành Long")
                .email("user3@gmail.com")
                .gender(User.EGender.MALE)
                .build());

        userDTOs.add(UserDTO.builder()
                .username("tackehoa")
                .passHash(DEFAULT_HASHED_PASS)
                .fullName("Tackle Hoa")
                .email("user5@gmail.com")
                .gender(User.EGender.FEMALE)
                .build());

        userDTOs.add(UserDTO.builder()
                .username("thaonguyen")
                .passHash(DEFAULT_HASHED_PASS)
                .fullName("Nguyên Thảo")
                .email("user6@gmail.com")
                .gender(User.EGender.FEMALE)
                .build());

        userDTOs.add(UserDTO.builder()
                .username("hanguyen")
                .passHash(DEFAULT_HASHED_PASS)
                .fullName("Nguyễn Hạ")
                .email("user7@gmail.com")
                .gender(User.EGender.FEMALE)
                .build());

        userDTOs.add(UserDTO.builder()
                .username("phuochoang")
                .passHash(DEFAULT_HASHED_PASS)
                .fullName("Phước Hoàng")
                .email("user8@gmail.com")
                .gender(User.EGender.FEMALE)
                .build());

        try {
            List<UserDTO> savedDTOs = new ArrayList<>();
            for (UserDTO userDTO : userDTOs) {
                UserDTO savedDTO = userService.save(userDTO);
                savedDTOs.add(savedDTO);
            }
            return savedDTOs;
        } catch (Exception e) {
            log.error(e.getMessage());
            return userDTOs;
        }
    }

    private ConversationDTO initPrivateConversation(UserDTO senderDTO, UserDTO recipientDTO) {
        List<ChatMessageDTO> messageDTOs = new ArrayList<>();

        LocalDateTime currentTime = LocalDateTime.now();

        String joinStr = String.format("`%s` joined!", senderDTO.getUsername());
        messageDTOs.add(ChatMessageDTO.builder()
                .content(joinStr)
                .type(ChatMessage.EType.NOTIFICATION)
                .sendingTime(currentTime)
                .senderUsername(senderDTO.getUsername())
                .build());
        currentTime = currentTime.plusSeconds(1);

        messageDTOs.add(ChatMessageDTO.builder()
                .content("Hi there!")
                .type(ChatMessage.EType.CHAT)
                .sendingTime(currentTime)
                .senderUsername(senderDTO.getUsername())
                .build());
        currentTime = currentTime.plusSeconds(1);

        messageDTOs.add(ChatMessageDTO.builder()
                .content("Hello!")
                .type(ChatMessage.EType.CHAT)
                .sendingTime(currentTime)
                .senderUsername(recipientDTO.getUsername())
                .build());
        currentTime = currentTime.plusSeconds(1);

        messageDTOs.add(ChatMessageDTO.builder()
                .content("Where do you live!")
                .type(ChatMessage.EType.CHAT)
                .sendingTime(currentTime)
                .senderUsername(recipientDTO.getUsername())
                .build());
        currentTime = currentTime.plusSeconds(1);

        messageDTOs.add(ChatMessageDTO.builder()
                .content("I live in my country! Hihi")
                .type(ChatMessage.EType.CHAT)
                .sendingTime(currentTime)
                .senderUsername(senderDTO.getUsername())
                .build());
        currentTime = currentTime.plusSeconds(1);

        messageDTOs.add(ChatMessageDTO.builder()
                .content("Let's check out the <span class=\"copyable-text\" " +
                        "onclick=\"copyToClipboardAndNotify('feature/crud-deck')\">feature/crud-deck</span> branch.")
                .type(ChatMessage.EType.CHAT)
                .sendingTime(currentTime)
                .senderUsername(senderDTO.getUsername())
                .build());
        currentTime = currentTime.plusSeconds(1);

        messageDTOs.add(ChatMessageDTO.builder()
                .content("<a href=\"https://www.quora.com/What-characters-are-invalid-in-a-URL\" " +
                        "target=\"_blank\">https://www.quora.com/What-characters-are-invalid-in-a-URL</a> " +
                        "<a href=\"https://www.quora.com/What-characters-are-invalid-in-a-URL\" " +
                        "target=\"_blank\">https://www.quora.com/What-characters-are-invalid-in-a-URL</a>")
                .type(ChatMessage.EType.CHAT)
                .sendingTime(currentTime)
                .senderUsername(senderDTO.getUsername())
                .build());
        currentTime = currentTime.plusSeconds(1);

        messageDTOs.add(ChatMessageDTO.builder()
                .content("<span class=\"copyable-text\" " +
                        "onclick=\"copyToClipboardAndNotify('https://getbootstrap.com/')\">https://getbootstrap.com/</span>")
                .type(ChatMessage.EType.CHAT)
                .sendingTime(currentTime)
                .senderUsername(senderDTO.getUsername())
                .build());

        Set<ConversationMemberDTO> memberDTOs = new HashSet<>();
        memberDTOs.add( new ConversationMemberDTO(senderDTO.getUsername(), ConversationMember.ERole.MEMBER) );
        memberDTOs.add( new ConversationMemberDTO(recipientDTO.getUsername(), ConversationMember.ERole.MEMBER) );

        ConversationDTO conversationDTO = new ConversationDTO();
        conversationDTO.setName( ConversationUtils.getPrivateConversationName(senderDTO.getUsername(), recipientDTO.getUsername()) );
        conversationDTO.setType(Conversation.EType.PRIVATE);
        conversationDTO.addMessageDTOs(messageDTOs);
        conversationDTO.addMemberDTOs(memberDTOs);

        ConversationDTO savedDTO = conversationService.saveConversationWithMessagesAndMembers(conversationDTO);

        return savedDTO;
    }

    public ConversationDTO initGroup(List<UserDTO> userDTOs) {
        UserDTO user0 = userDTOs.get(0) != null ? userDTOs.get(0) : new UserDTO();
        UserDTO user1 = userDTOs.get(1) != null ? userDTOs.get(1) : new UserDTO();
        UserDTO user2 = userDTOs.get(2) != null ? userDTOs.get(2) : new UserDTO();
        UserDTO user3 = userDTOs.get(3) != null ? userDTOs.get(3) : new UserDTO();

        List<ChatMessageDTO> messageDTOs = new ArrayList<>();

        LocalDateTime currentTime = LocalDateTime.now();

        String joinStr = String.format("`%s` joined!", user0.getUsername());
        messageDTOs.add(ChatMessageDTO.builder()
                .content(joinStr)
                .type(ChatMessage.EType.NOTIFICATION)
                .sendingTime(currentTime)
                .senderUsername(user0.getUsername())
                .build());
        currentTime = currentTime.plusSeconds(1);

        messageDTOs.add(ChatMessageDTO.builder()
                .content("Hi there!")
                .type(ChatMessage.EType.CHAT)
                .sendingTime(currentTime)
                .senderUsername(user0.getUsername())
                .build());
        currentTime = currentTime.plusSeconds(1);

        messageDTOs.add(ChatMessageDTO.builder()
                .content("Hi there!")
                .type(ChatMessage.EType.CHAT)
                .sendingTime(currentTime)
                .senderUsername(user1.getUsername())
                .build());
        currentTime = currentTime.plusSeconds(1);

        messageDTOs.add(ChatMessageDTO.builder()
                .content("Hello!")
                .type(ChatMessage.EType.CHAT)
                .sendingTime(currentTime)
                .senderUsername(user1.getUsername())
                .build());
        currentTime = currentTime.plusSeconds(1);

        messageDTOs.add(ChatMessageDTO.builder()
                .content("Where do you live!")
                .type(ChatMessage.EType.CHAT)
                .sendingTime(currentTime)
                .senderUsername(user2.getUsername())
                .build());
        currentTime = currentTime.plusSeconds(1);

        messageDTOs.add(ChatMessageDTO.builder()
                .content("I live in my country! Hihi")
                .type(ChatMessage.EType.CHAT)
                .sendingTime(currentTime)
                .senderUsername(user3.getUsername())
                .build());
        currentTime = currentTime.plusSeconds(1);

        messageDTOs.add(ChatMessageDTO.builder()
                .content("Let's check out the <span class=\"copyable-text\" " +
                        "onclick=\"copyToClipboardAndNotify('feature/crud-deck')\">feature/crud-deck</span> branch.")
                .type(ChatMessage.EType.CHAT)
                .sendingTime(currentTime)
                .senderUsername(user3.getUsername())
                .build());
        currentTime = currentTime.plusSeconds(1);

        messageDTOs.add(ChatMessageDTO.builder()
                .content("<a href=\"https://www.quora.com/What-characters-are-invalid-in-a-URL\" " +
                        "target=\"_blank\">https://www.quora.com/What-characters-are-invalid-in-a-URL</a> " +
                        "<a href=\"https://www.quora.com/What-characters-are-invalid-in-a-URL\" " +
                        "target=\"_blank\">https://www.quora.com/What-characters-are-invalid-in-a-URL</a>")
                .type(ChatMessage.EType.CHAT)
                .sendingTime(currentTime)
                .senderUsername(user2.getUsername())
                .build());
        currentTime = currentTime.plusSeconds(1);

        messageDTOs.add(ChatMessageDTO.builder()
                .content("<span class=\"copyable-text\" " +
                        "onclick=\"copyToClipboardAndNotify('https://getbootstrap.com/')\">https://getbootstrap.com/</span>")
                .type(ChatMessage.EType.CHAT)
                .sendingTime(currentTime)
                .senderUsername(user2.getUsername())
                .build());

        Set<ConversationMemberDTO> members = new HashSet<>();
        members.add( new ConversationMemberDTO(user0.getUsername(), ConversationMember.ERole.ADMIN) );
        members.add( new ConversationMemberDTO(user1.getUsername(), ConversationMember.ERole.MEMBER) );
        members.add( new ConversationMemberDTO(user2.getUsername(), ConversationMember.ERole.MEMBER) );
        members.add( new ConversationMemberDTO(user3.getUsername(), ConversationMember.ERole.MEMBER) );

        ConversationDTO conversationDTO = ConversationDTO.builder()
                .type(Conversation.EType.GROUP)
                .name("Nhóm Lập trình Web | Nhóm Lập trình Web | Nhóm Lập trình Web")
                .build();
        conversationDTO.addMessageDTOs(messageDTOs);
        conversationDTO.addMemberDTOs(members);

        ConversationDTO saved = conversationService.saveConversationWithMessagesAndMembers(conversationDTO);

        return saved;
    }
}
