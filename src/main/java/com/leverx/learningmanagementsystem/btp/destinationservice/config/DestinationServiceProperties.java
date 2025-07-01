package com.leverx.learningmanagementsystem.btp.destinationservice.config;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "destination-service")
@Component
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DestinationServiceProperties {

    private String url;

    private String uri;

    private String clientId;

    private String clientSecret;

    private String xsAppName;
}
