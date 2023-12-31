package com.vanannek.authentication;

public record LoginRequest(
        String username,
        String plainPass
) { }
