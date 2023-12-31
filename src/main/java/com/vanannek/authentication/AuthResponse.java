package com.vanannek.authentication;

import java.util.Date;

public record AuthResponse(
        String username,
        String accessToken,
        String tokenType,
        Date exp
) {}
