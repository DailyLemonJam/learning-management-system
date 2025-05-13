package com.leverx.learningmanagementsystem.sap.destination.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record AccessTokenResponseDto(
        @JsonProperty("access_token") String accessToken,
        @JsonProperty("token_type") String tokenType,
        @JsonProperty("expires_in") Integer expiresIn,
        String scope,
        String jti
) {
}
