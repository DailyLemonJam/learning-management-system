package com.leverx.learningmanagementsystem.email.smtpprovider.config;

import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Builder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "user-service-mail")
@Component
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SmtpServerProperties {

    private String user;

    private String password;

    private String from;

    private String host;

    private String port;

    private String protocol;
}