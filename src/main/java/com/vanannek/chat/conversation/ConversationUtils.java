package com.vanannek.chat.conversation;

public class ConversationUtils {
    public static final String RETRIEVED_CONVERSATIONS = "Retrieved conversations for user '{}'. Count: {}";
    public static final String CONVERSATION_DELETED_SUCCESSFULLY = "Conversation deleted successfully";
    public static final String MEMBER_ADDED_SUCCESSFULLY = "Member added to conversation successfully";
    public static final String MEMBER_DELETED_SUCCESSFULLY = "Member deleted from conversation successfully";
    public static final String PRIVATE_CONVERSATION_ADDED_SUCCESSFULLY = "A private conversation added successfully";
    public static final String GROUP_ADDED_SUCCESSFULLY = "A group added successfully";

    private static final String KEY_PATTERN = "%s&%s";

    public static String getPrivateConversationName(String firstUser, String secondUser) {
        String privateConverstaionName = "";
        if (firstUser.compareTo(secondUser) > 0) {
            privateConverstaionName = String.format(KEY_PATTERN, firstUser, secondUser);
        } else {
            privateConverstaionName = String.format(KEY_PATTERN, secondUser, firstUser);
        }
        return privateConverstaionName;
    }

    public static String getRemainderUser(String privateConversationName, String ignoredUser) {
        String[] splits = privateConversationName.split("&");
        return splits[0].equals(ignoredUser) ? splits[1] : splits[0];
    }
}
