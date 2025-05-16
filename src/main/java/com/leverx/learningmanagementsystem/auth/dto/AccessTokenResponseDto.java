package com.leverx.learningmanagementsystem.auth.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record AccessTokenResponseDto(
        String accessToken,
        String tokenType,
        Integer expiresIn,
        String scope,
        String jti
) {}
