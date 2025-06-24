package com.leverx.starterfeatureflags.client;

import com.leverx.starterfeatureflags.config.FeatureFlagsServiceProperties;
import com.leverx.starterfeatureflags.dto.FeatureFlagsResponseDto;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Base64;

public class FeatureFlagsServiceImpl implements FeatureFlagsService {

    private static final String EVALUATE_ENDPOINT = "/api/v2/evaluate/";

    private final FeatureFlagsServiceProperties featureFlagsServiceProperties;
    private final RestClient restClient;

    public FeatureFlagsServiceImpl(RestClient restClient, FeatureFlagsServiceProperties featureFlagsServiceProperties) {
        this.featureFlagsServiceProperties = featureFlagsServiceProperties;
        this.restClient = restClient;
    }

    @Override
    public FeatureFlagsResponseDto getFeatureFlag(String name) {
        var uri = buildUri(name);
        var headers = buildHeaders();
        return restClient.get()
                .uri(uri)
                .headers(h -> h.addAll(headers))
                .retrieve()
                .body(FeatureFlagsResponseDto.class);
    }

    private String buildUri(String name) {
        return UriComponentsBuilder
                .fromUriString(featureFlagsServiceProperties.uri())
                .pathSegment(EVALUATE_ENDPOINT, name)
                .toUriString();
    }

    private HttpHeaders buildHeaders() {
        var headers = new HttpHeaders();
        var encoder = Base64.getEncoder();

        var username = featureFlagsServiceProperties.username();
        var password = featureFlagsServiceProperties.password();
        var stringToEncode = username + ":" + password;
        var encodedCredentials = encoder.encodeToString(stringToEncode.getBytes());

        headers.setBasicAuth(encodedCredentials);
        return headers;
    }
}
