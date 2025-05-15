package com.leverx.learningmanagementsystem.btp.featureflag.config;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "feature-flag-service")
@Component
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FeatureFlagProperties {
    private String uri;
    private String username;
    private String password;
}
