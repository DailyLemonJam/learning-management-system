package com.leverx.starterfeatureflags.config;

import com.leverx.starterfeatureflags.client.FeatureFlagsServiceImpl;
import com.leverx.starterfeatureflags.client.FeatureFlagsService;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestClient;

@AutoConfiguration
@EnableConfigurationProperties(FeatureFlagsServiceProperties.class)
public class FeatureFlagsServiceConfiguration {

    private final FeatureFlagsServiceProperties featureFlagsServiceProperties;

    public FeatureFlagsServiceConfiguration(FeatureFlagsServiceProperties featureFlagsServiceProperties) {
        this.featureFlagsServiceProperties = featureFlagsServiceProperties;
    }

    @Bean
    @ConditionalOnMissingBean(RestClient.class)
    RestClient restClient() {
        return RestClient.create();
    }

    @Bean
    FeatureFlagsService featureFlagsServiceImpl(RestClient restClient) {
        return new FeatureFlagsServiceImpl(restClient, featureFlagsServiceProperties);
    }
}
