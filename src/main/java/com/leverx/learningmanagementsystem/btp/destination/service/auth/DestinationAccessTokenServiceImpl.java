package com.leverx.learningmanagementsystem.btp.destination.service.auth;

import com.leverx.learningmanagementsystem.btp.destination.dto.AccessTokenResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.Map;

import static org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED;

@Service
@Profile("cloud")
@RequiredArgsConstructor
public class DestinationAccessTokenServiceImpl implements DestinationAccessTokenService {
    private static final String OAUTH_API = "/oauth/token";
    private static final String EMPTY = "";

    private static final String GRANT_TYPE = "grant_type";
    private static final String CLIENT_CREDENTIALS = "client_credentials";
    private static final String CLIENT_ID = "client_id";
    private static final String CLIENT_SECRET = "client_secret";

    @Cacheable(cacheNames = "destinationServiceAuthToken", key = "#clientId + '_' + #clientSecret")
    @Override
    public String getDestinationAccessToken(String clientId, String clientSecret, String url) {
        var tokenUri = createAccessTokenUri(url);
        var credentials = createAccessTokenCredentials(clientId, clientSecret);
        var response = RestClient.create().post()
                .uri(tokenUri)
                .body(credentials)
                .contentType(APPLICATION_FORM_URLENCODED)
                .retrieve()
                .body(AccessTokenResponseDto.class);
        return response != null ? response.accessToken() : EMPTY;
    }

    private String createAccessTokenUri(String url) {
        var builder = new StringBuilder();
        builder.append(url);
        builder.append(OAUTH_API);
        return builder.toString();
    }

    private Map<String, String> createAccessTokenCredentials(String clientId, String clientSecret) {
        return Map.of(
                GRANT_TYPE, CLIENT_CREDENTIALS,
                CLIENT_ID, clientId,
                CLIENT_SECRET, clientSecret
        );
    }

}
