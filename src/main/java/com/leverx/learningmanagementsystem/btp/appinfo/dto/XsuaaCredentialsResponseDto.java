package com.leverx.learningmanagementsystem.btp.appinfo.dto;

public record XsuaaCredentialsResponseDto(

        String tokenUrl,

        String clientId,

        String clientSecret
) {
}
