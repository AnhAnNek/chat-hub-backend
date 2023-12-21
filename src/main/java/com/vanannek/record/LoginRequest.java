package com.vanannek.record;

public record LoginRequest(
        String curUsername,
        String plainPass
) { }
