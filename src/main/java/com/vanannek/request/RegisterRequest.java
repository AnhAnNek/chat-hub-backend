package com.vanannek.request;

import com.vanannek.entity.User;

public record RegisterRequest(
        String username,
        String plainPass,
        String fullName,
        String email,
        User.EGender gender
) {}
