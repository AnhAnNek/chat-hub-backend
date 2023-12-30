package com.vanannek.response;

import java.util.Date;

public record AuthResponse(
        String username,
        String accessToken,
        String tokenType,
        Date exp
) {}
