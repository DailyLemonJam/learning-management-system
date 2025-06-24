package com.leverx.learningmanagementsystem.web.oauth2.client;

import com.leverx.learningmanagementsystem.web.oauth2.dto.AccessTokenResponseDto;
import com.leverx.learningmanagementsystem.web.oauth2.exception.OAuth2TokenClientBadResponseException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Map;

import static java.util.Objects.nonNull;
import static org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED;

@Slf4j
@Component
@RequiredArgsConstructor
public class OAuth2TokenClientImpl implements OAuth2TokenClient {

    private static final String OAUTH = "oauth";
    private static final String TOKEN = "token";
    private static final String GRANT_TYPE = "grant_type";
    private static final String CLIENT_CREDENTIALS = "client_credentials";
    private static final String CLIENT_ID = "client_id";
    private static final String CLIENT_SECRET = "client_secret";

    private static final RestClient restClient = RestClient.builder().build();

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

        if (nonNull(response)) {
            return response.accessToken();
        }
        throw new OAuth2TokenClientBadResponseException("Unable to get access token");
    }

    private String createAccessTokenUri(String tokenUrl) {
        return UriComponentsBuilder
                .fromUriString(tokenUrl)
                .pathSegment(OAUTH, TOKEN)
                .toUriString();
    }

    private MultiValueMap<String, String> createAccessTokenCredentials(String clientId, String clientSecret) {
        var map = Map.of(
                GRANT_TYPE, CLIENT_CREDENTIALS,
                CLIENT_ID, clientId,
                CLIENT_SECRET, clientSecret
        );
        return MultiValueMap.fromSingleValue(map);
    }
}
