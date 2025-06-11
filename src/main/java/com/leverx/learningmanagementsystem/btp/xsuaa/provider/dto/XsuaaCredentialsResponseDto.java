package com.leverx.learningmanagementsystem.btp.xsuaa.provider.dto;

public record XsuaaCredentialsResponseDto(

        String tokenUrl,

        String clientId,

        String clientSecret
) {
}
