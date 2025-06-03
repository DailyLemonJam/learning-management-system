package com.leverx.learningmanagementsystem.btp.featureflagservice.service;

import com.leverx.learningmanagementsystem.btp.featureflagservice.config.FeatureFlagProperties;
import com.leverx.learningmanagementsystem.btp.featureflagservice.dto.FeatureFlagResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Base64;

@Service
@Profile("cloud")
@RequiredArgsConstructor
public class CloudFeatureFlagService implements FeatureFlagService {

    private static final String EVALUATE_ENDPOINT = "/api/v2/evaluate/";
    private static final String AUTHORIZATION = "AUTHORIZATION";
    private static final String BASIC = "Basic ";

    private final RestClient restClient;
    private final FeatureFlagProperties featureFlagProperties;

    @Override
    public FeatureFlagResponseDto getFeatureFlag(String name) {
        var uri = createFeatureFlagUri(name);
        var authHeader = createAuthHeader(featureFlagProperties);
        return restClient.get()
                .uri(uri)
                .header(AUTHORIZATION, authHeader)
                .retrieve()
                .body(FeatureFlagResponseDto.class);
    }

    private String createFeatureFlagUri(String name) {
        var uriComponents = UriComponentsBuilder.newInstance()
                .host(featureFlagProperties.getUri())
                .pathSegment(EVALUATE_ENDPOINT, name)
                .build();
        return uriComponents.toUriString();
    }

    private String createAuthHeader(FeatureFlagProperties featureFlagProperties) {
        var encoder = Base64.getEncoder();
        var username = featureFlagProperties.getUsername();
        var password = featureFlagProperties.getPassword();
        var stringToEncode = username + ":" + password;

        var builder = new StringBuilder();
        builder.append(BASIC);
        builder.append(encoder.encodeToString(stringToEncode.getBytes()));
        return builder.toString();
    }

}
