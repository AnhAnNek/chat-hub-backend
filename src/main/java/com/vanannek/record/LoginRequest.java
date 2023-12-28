package com.vanannek.record;

public record LoginRequest(
        String username,
        String plainPass
) { }
