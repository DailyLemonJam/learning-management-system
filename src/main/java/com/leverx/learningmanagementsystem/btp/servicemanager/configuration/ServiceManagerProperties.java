package com.leverx.learningmanagementsystem.btp.servicemanager.configuration;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Configuration
@Profile("cloud")
@ConfigurationProperties(prefix = "service-manager")
@Component
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ServiceManagerProperties {

    private String authUrl;

    private String serviceManagerUrl;

    private String clientId;

    private String clientSecret;

    private String xsAppName;
}
