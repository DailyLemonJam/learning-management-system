package com.leverx.learningmanagementsystem.application.config;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "application-variables")
@Component
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ApplicationProperties {

    private String applicationUri;

    private String applicationName;

    private String providerSubdomain;
}
