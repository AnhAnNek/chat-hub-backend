package com.vanannek.request;

public record LoginRequest(
        String username,
        String plainPass
) { }
