package com.leverx.learningmanagementsystem.btp.destination.service;

import com.leverx.learningmanagementsystem.btp.destination.config.DestinationServiceProperties;
import com.leverx.learningmanagementsystem.btp.destination.dto.DestinationResponseDto;
import com.leverx.learningmanagementsystem.btp.destination.service.auth.DestinationAccessTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;

@Service
@Profile("cloud")
@RequiredArgsConstructor
public class DestinationServiceImpl implements DestinationService {
    private static final String API = "/destination-configuration/v1/destinations/";

    private static final String AUTHORIZATION = "AUTHORIZATION";
    private static final String BEARER = "Bearer ";

    private final DestinationServiceProperties destinationServiceProperties;
    private final DestinationAccessTokenService destinationAccessTokenService;

    @Override
    @Retryable(
            retryFor = HttpClientErrorException.Unauthorized.class,
            maxAttempts = 3,
            backoff = @Backoff(delay = 2000)
    )
    public DestinationResponseDto getByName(String name) {
        var destinationUri = createDestinationUri(name);
        var accessToken = destinationAccessTokenService.getDestinationAccessToken(
                destinationServiceProperties.getClientId(),
                destinationServiceProperties.getClientSecret(),
                destinationServiceProperties.getUrl());
        var authHeader = BEARER + accessToken;
        return RestClient.create().get()
                .uri(destinationUri)
                .header(AUTHORIZATION, authHeader)
                .retrieve()
                .body(DestinationResponseDto.class);
    }

    private String createDestinationUri(String name) {
        var builder = new StringBuilder();
        builder.append(destinationServiceProperties.getUri());
        builder.append(API);
        builder.append(name);
        return builder.toString();
    }

}
