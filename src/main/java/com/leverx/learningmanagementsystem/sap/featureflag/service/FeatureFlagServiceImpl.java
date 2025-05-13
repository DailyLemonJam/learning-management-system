package com.leverx.learningmanagementsystem.sap.featureflag.service;

import com.leverx.learningmanagementsystem.sap.featureflag.config.FeatureFlagProperties;
import com.leverx.learningmanagementsystem.sap.featureflag.dto.FeatureFlagResponseDto;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.Base64;

@Service
@Profile("sap-cloud")
public class FeatureFlagServiceImpl implements FeatureFlagService {
    private final String route;
    private final String authHeader;

    private static final String API = "/api/v2/evaluate/";
    private static final String AUTHORIZATION = "AUTHORIZATION";
    private static final String BASIC = "Basic ";

    public FeatureFlagServiceImpl(FeatureFlagProperties featureFlagProperties) {
        route = createRoute(featureFlagProperties);
        authHeader = createAuthHeader(featureFlagProperties);
    }

    @Override
    public FeatureFlagResponseDto getFeatureFlag(String featureFlagName) {
        var path = route + featureFlagName;
        return RestClient.create().get()
                .uri(path)
                .header(AUTHORIZATION, authHeader)
                .retrieve()
                .body(FeatureFlagResponseDto.class);
    }

    private String createRoute(FeatureFlagProperties featureFlagProperties) {
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
