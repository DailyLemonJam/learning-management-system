package com.leverx.starterfeatureflags.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "feature-flags-service")
public record FeatureFlagsServiceProperties(

        String uri,

        String username,

        String password
) {
}
