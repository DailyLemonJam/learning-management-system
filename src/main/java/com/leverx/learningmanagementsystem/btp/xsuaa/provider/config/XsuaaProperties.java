package com.leverx.learningmanagementsystem.btp.xsuaa.provider.config;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ConfigurationProperties(prefix = "xsuaa-service")
public class XsuaaProperties {

    private String tokenUrl;

    private String clientId;

    private String clientSecret;
}
