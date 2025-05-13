package com.leverx.learningmanagementsystem.email.smtpselector.config;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "user-service-mail")
@Component
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SmtpServerProperties {
    private String user;
    private String password;
    private String from;
    private String host;
    private String port;
    private String protocol;
}