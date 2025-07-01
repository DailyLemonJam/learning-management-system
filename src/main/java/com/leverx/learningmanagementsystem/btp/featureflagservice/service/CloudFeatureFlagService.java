package com.leverx.learningmanagementsystem.btp.featureflagservice.service;

import com.leverx.learningmanagementsystem.btp.featureflagservice.model.FeatureFlagProperties;
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

    private final RestClient restClient;
    private final FeatureFlagProperties featureFlagProperties;

    @Override
    public FeatureFlagResponseDto getByName(String name) {
        var uri = createFeatureFlagUri(name);

        String username = featureFlagProperties.getUsername();
        String password = featureFlagProperties.getPassword();

        return restClient.get()
                .uri(uri)
                .headers(headers -> headers.setBasicAuth(username, password))
                .retrieve()
                .body(FeatureFlagResponseDto.class);
    }

    private String createFeatureFlagUri(String name) {
        return UriComponentsBuilder
                .fromUriString(featureFlagProperties.getUri())
                .pathSegment(EVALUATE_ENDPOINT, name)
                .toUriString();
    }
}
