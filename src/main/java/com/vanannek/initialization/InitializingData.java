package com.vanannek.initialization;

import com.vanannek.chat.conversation.Conversation;
import com.vanannek.chat.conversation.ConversationDTO;
import com.vanannek.chat.conversation.ConversationUtils;
import com.vanannek.chat.conversation.service.ConversationService;
import com.vanannek.chat.member.ConversationMember;
import com.vanannek.chat.member.ConversationMemberDTO;
import com.vanannek.chat.message.ChatMessage;
import com.vanannek.chat.message.ChatMessageDTO;
import com.vanannek.notification.NotificationDTO;
import com.vanannek.notification.service.NotificationService;
import com.vanannek.socialmedia.EReactionType;
import com.vanannek.socialmedia.comment.CommentDTO;
import com.vanannek.socialmedia.post.Post;
import com.vanannek.socialmedia.post.PostDTO;
import com.vanannek.socialmedia.post.service.PostService;
import com.vanannek.socialmedia.postreaction.PostReactionDTO;
import com.vanannek.user.User;
import com.vanannek.user.UserDTO;
import com.vanannek.user.service.UserService;
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
    private final NotificationService notificationService;
    private final PostService postService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        deleteAll();
        initData();
    }

    private void deleteAll() {
        conversationService.deleteAll();
        userService.deleteAll();
        notificationService.deleteAll();
    }

    private void initData() {
        List<UserDTO> userDTOs = initUsers();
        ConversationDTO savedPrivateConversationDTO = initPrivateConversation(userDTOs.get(0), userDTOs.get(1));
        ConversationDTO savedPrivateConversationDTO1 = initPrivateConversation(userDTOs.get(2), userDTOs.get(0));
        ConversationDTO savedPrivateConversationDTO3 = initPrivateConversation(userDTOs.get(0), userDTOs.get(3));
        ConversationDTO savedGroup = initGroup(userDTOs);

        List<NotificationDTO> savedNotificationDTOs = initNotifications(userDTOs.get(0));

        PostDTO savedPost = initPost(userDTOs);
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

    public List<NotificationDTO> initNotifications(UserDTO userDTO) {
        String username = userDTO != null ? userDTO.getUsername() : "";
        NotificationDTO notification1 = new NotificationDTO("New message received", username);
        NotificationDTO notification2 = new NotificationDTO("Meeting reminder", username);
        NotificationDTO notification3 = new NotificationDTO("You have a new follower", username);
        NotificationDTO notification4 = new NotificationDTO("Update: Your post has comments", username);
        NotificationDTO notification5 = new NotificationDTO("Update: Your post has comments", username);

        List<NotificationDTO> notificationDTOs =
                List.of(notification1, notification2, notification3, notification4, notification5);

        List<NotificationDTO> savedNotificationDTOs = new ArrayList<>();
        for (NotificationDTO notificationDTO : notificationDTOs) {
            NotificationDTO savedDTO = notificationService.add(notificationDTO);
            savedNotificationDTOs.add(savedDTO);
        }

        return savedNotificationDTOs;
    }

    public PostDTO initPost(List<UserDTO> userDTOs) {
        String ownerUsername = userDTOs.get(0).getUsername();

        List<PostReactionDTO> postReactionDTOs = new ArrayList<>();
        List<CommentDTO> commentDTOs = new ArrayList<>();

        for (UserDTO userDTO : userDTOs) {
            String username = userDTO.getUsername();
            postReactionDTOs.add( new PostReactionDTO(username, EReactionType.LIKE.name()) );

            commentDTOs.add( CommentDTO.builder()
                            .content(username + " hihihi!")
                            .createdAt(LocalDateTime.now())
                            .updatedAt(LocalDateTime.now())
                            .username(username)
                            .build() );
        }

        PostDTO postDTO = PostDTO.builder()
                .content("Post content")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .status(Post.EStatus.ACTIVE.toString())
                .username(ownerUsername)
                .build();
        postDTO.addReactions(postReactionDTOs);
        postDTO.addComments(commentDTOs);

        PostDTO saved = postService.savePostWithReactionsAndComments(postDTO);
        return saved;
    }
}
