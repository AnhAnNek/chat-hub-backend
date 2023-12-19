package com.vanannek.util;

public class ConversationUtils {
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
