package com.vanannek.authentication;

import com.vanannek.user.User;

public record RegisterRequest(
        String username,
        String plainPass,
        String fullName,
        String email,
        User.EGender gender
) {}
