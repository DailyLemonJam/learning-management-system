package com.leverx.learningmanagementsystem.auth.client;

import com.leverx.learningmanagementsystem.auth.exception.OAuth2TokenClientBadResponseException;
import com.leverx.learningmanagementsystem.auth.dto.AccessTokenResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Map;

import static org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED;

@Component
@Profile("cloud")
@RequiredArgsConstructor
public class OAuth2TokenClientImpl implements OAuth2TokenClient {
    private static final String OAUTH_ENDPOINT = "/oauth/token";
    private static final String GRANT_TYPE = "grant_type";
    private static final String CLIENT_CREDENTIALS = "client_credentials";
    private static final String CLIENT_ID = "client_id";
    private static final String CLIENT_SECRET = "client_secret";

    private final RestClient restClient;

    @Override
    public String getToken(String clientId, String clientSecret, String tokenUrl) {
        var tokenUri = createAccessTokenUri(tokenUrl);
        var credentials = createAccessTokenCredentials(clientId, clientSecret);
        var response = restClient.post()
                .uri(tokenUri)
                .body(credentials)
                .contentType(APPLICATION_FORM_URLENCODED)
                .retrieve()
                .body(AccessTokenResponseDto.class);
        if (response != null) {
            return response.accessToken();
        }
        throw new OAuth2TokenClientBadResponseException("Unable to get access token");
    }

    private String createAccessTokenUri(String tokenUrl) {
        var uriComponents = UriComponentsBuilder.newInstance()
                .host(tokenUrl)
                .path(OAUTH_ENDPOINT)
                .build();
        return uriComponents.toUriString();
    }

    private Map<String, String> createAccessTokenCredentials(String clientId, String clientSecret) {
        return Map.of(
                GRANT_TYPE, CLIENT_CREDENTIALS,
                CLIENT_ID, clientId,
                CLIENT_SECRET, clientSecret
        );
    }

}
