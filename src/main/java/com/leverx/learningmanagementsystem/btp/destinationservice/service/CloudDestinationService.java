package com.leverx.learningmanagementsystem.btp.destinationservice.service;

import com.leverx.learningmanagementsystem.btp.destinationservice.config.DestinationServiceProperties;
import com.leverx.learningmanagementsystem.btp.destinationservice.dto.DestinationResponseDto;
import com.leverx.learningmanagementsystem.btp.destinationservice.service.auth.DestinationServiceAccessTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.context.annotation.Profile;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException.Unauthorized;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.UriComponentsBuilder;

@Slf4j
@Service
@Profile("cloud")
@RequiredArgsConstructor
public class CloudDestinationService implements DestinationService {

    private static final String DESTINATION_ENDPOINT = "/destination-configuration/v1/destinations/";

    private final RestClient restClient;
    private final DestinationServiceProperties destinationServiceProperties;
    private final DestinationServiceAccessTokenProvider destinationServiceAccessTokenProvider;

    @Override
    @Retryable(retryFor = Unauthorized.class, maxAttempts = 2)
    public DestinationResponseDto getByName(String name) {
        return tryToGet(name);
    }

    private DestinationResponseDto tryToGet(String name) {
        // TODO: if tenant exists in Context -> get Destination token from Subscriber Xsuaa
        // If no Destination found -> get Destination token from Provider Xsuaa
        // URL for getting Destinations keeps the same
        try {
            var destinationUri = createDestinationUri(name);
            var headers = createHeaders();
            return restClient.get()
                    .uri(destinationUri)
                    .headers(headers::addAll)
                    .retrieve()
                    .body(DestinationResponseDto.class);
        } catch (Unauthorized e) {
            refreshAccessToken();
            throw e;
        }
    }

    private String createDestinationUri(String name) {
        return UriComponentsBuilder
                .fromUriString(destinationServiceProperties.getUri())
                .pathSegment(DESTINATION_ENDPOINT, name)
                .toUriString();
    }

    private HttpHeaders createHeaders() {
        var accessToken = getAccessToken();
        var headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);
        return headers;
    }

    private String getAccessToken() {
        return destinationServiceAccessTokenProvider.getAccessToken(
                destinationServiceProperties.getClientId(),
                destinationServiceProperties.getClientSecret(),
                destinationServiceProperties.getUrl());
    }

    private void refreshAccessToken() {
        destinationServiceAccessTokenProvider.refreshAccessToken(destinationServiceProperties.getClientId(),
                destinationServiceProperties.getClientSecret(), destinationServiceProperties.getUrl());
    }
}
