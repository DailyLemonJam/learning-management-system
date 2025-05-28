package com.leverx.learningmanagementsystem.btp.destination.service;

import com.leverx.learningmanagementsystem.btp.destination.config.DestinationServiceProperties;
import com.leverx.learningmanagementsystem.btp.destination.dto.DestinationResponseDto;
import com.leverx.learningmanagementsystem.btp.destination.service.auth.DestinationServiceAccessTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException.Unauthorized;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.UriComponentsBuilder;

@Slf4j
@Service
@Profile("cloud")
@RequiredArgsConstructor
public class DestinationServiceImpl implements DestinationService {
    private static final String DESTINATION_ENDPOINT = "/destination-destinationConfiguration/v1/destinations/";

    private static final String AUTHORIZATION = "AUTHORIZATION";
    private static final String BEARER = "Bearer ";

    private final RestClient restClient;
    private final DestinationServiceProperties destinationServiceProperties;
    private final DestinationServiceAccessTokenProvider destinationServiceAccessTokenProvider;

    @Override
    @Retryable(retryFor = Unauthorized.class, maxAttempts = 2)
    public DestinationResponseDto getByName(String name) {
        return tryToGet(name);
    }

    private DestinationResponseDto tryToGet(String name) {
        try {
            var destinationUri = createDestinationUri(name);
            var accessToken = destinationServiceAccessTokenProvider.getAccessToken(
                    destinationServiceProperties.getClientId(),
                    destinationServiceProperties.getClientSecret(),
                    destinationServiceProperties.getUrl());
            var authHeader = BEARER + accessToken;
            return restClient.get()
                    .uri(destinationUri)
                    .header(AUTHORIZATION, authHeader)
                    .retrieve()
                    .body(DestinationResponseDto.class);
        } catch (Unauthorized e) {
            destinationServiceAccessTokenProvider.refreshAccessToken(destinationServiceProperties.getClientId(),
                    destinationServiceProperties.getClientSecret(), destinationServiceProperties.getUrl());
            throw e;
        }
    }

    private String createDestinationUri(String name) {
        var uriComponents = UriComponentsBuilder.newInstance()
                .host(destinationServiceProperties.getUri())
                .pathSegment(DESTINATION_ENDPOINT, name)
                .build();
        return uriComponents.toUriString();
    }

}
