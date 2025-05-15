package com.leverx.learningmanagementsystem.btp.featureflag.service;

import com.leverx.learningmanagementsystem.btp.featureflag.config.FeatureFlagProperties;
import com.leverx.learningmanagementsystem.btp.featureflag.dto.FeatureFlagResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.Base64;

@Service
@Profile("cloud")
@RequiredArgsConstructor
public class FeatureFlagServiceImpl implements FeatureFlagService {
    private static final String API = "/api/v2/evaluate/";
    private static final String AUTHORIZATION = "AUTHORIZATION";
    private static final String BASIC = "Basic ";

    private final FeatureFlagProperties featureFlagProperties;

    @Override
    @Retryable
    public FeatureFlagResponseDto getFeatureFlag(String featureFlagName) {
        var path = createUrl(featureFlagProperties) + featureFlagName;
        var authHeader = createAuthHeader(featureFlagProperties);
        return RestClient.create().get()
                .uri(path)
                .header(AUTHORIZATION, authHeader)
                .retrieve()
                .body(FeatureFlagResponseDto.class);
    }

    private String createUrl(FeatureFlagProperties featureFlagProperties) {
        var builder = new StringBuilder();
        builder.append(featureFlagProperties.getUri());
        builder.append(API);
        return builder.toString();
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
