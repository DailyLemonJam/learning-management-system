package com.leverx.learningmanagementsystem.btp.destinationservice.client;

import com.leverx.learningmanagementsystem.btp.destinationservice.model.DestinationServiceProperties;
import com.leverx.learningmanagementsystem.btp.destinationservice.dto.DestinationResponseDto;
import com.leverx.learningmanagementsystem.web.oauth2.dto.OAuth2ClientCredentials;
import com.leverx.learningmanagementsystem.btp.destinationservice.auth.DestinationServiceAccessTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpHeaders;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.UriComponentsBuilder;

@Slf4j
@Component
@Profile("cloud")
@RequiredArgsConstructor
public class DestinationServiceClientImpl implements DestinationServiceClient {

    private static final String DESTINATION_ENDPOINT = "/destination-configuration/v1/destinations/";

    private final RestClient restClient;
    private final DestinationServiceAccessTokenProvider destinationServiceAccessTokenProvider;
    private final DestinationServiceProperties destinationServiceProperties;

    @Retryable(retryFor = HttpClientErrorException.Unauthorized.class, maxAttempts = 2)
    public DestinationResponseDto getByName(String name, OAuth2ClientCredentials clientCredentials) {
        return tryGetByName(name, clientCredentials);
    }

    private DestinationResponseDto tryGetByName(String name, OAuth2ClientCredentials clientCredentials) {
        try {
            var destinationUri = createDestinationUri(name);
            var headers = createHeaders(clientCredentials);
            return restClient.get()
                    .uri(destinationUri)
                    .headers(h -> h.addAll(headers))
                    .retrieve()
                    .body(DestinationResponseDto.class);
        } catch (HttpClientErrorException.Unauthorized e) {
            refreshAccessToken(clientCredentials);
            throw e;
        }
    }

    private String createDestinationUri(String name) {
        return UriComponentsBuilder
                .fromUriString(destinationServiceProperties.getUri())
                .pathSegment(DESTINATION_ENDPOINT, name)
                .toUriString();
    }

    private HttpHeaders createHeaders(OAuth2ClientCredentials clientCredentials) {
        String accessToken = getAccessToken(clientCredentials);

        var headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);
        return headers;
    }

    private String getAccessToken(OAuth2ClientCredentials clientCredentials) {
        return destinationServiceAccessTokenProvider.getAccessToken(
                clientCredentials.clientId(),
                clientCredentials.clientSecret(),
                clientCredentials.url());
    }

    private void refreshAccessToken(OAuth2ClientCredentials clientCredentials) {
        destinationServiceAccessTokenProvider.refreshAccessToken(
                clientCredentials.clientId(),
                clientCredentials.clientSecret(),
                clientCredentials.url());
    }
}
