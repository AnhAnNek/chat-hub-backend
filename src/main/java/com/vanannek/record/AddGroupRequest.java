package com.vanannek.record;

import java.util.List;

public record AddGroupRequest(
        String curUsername,
        String conversationName,
        List<String> memberUsernames
) {}
