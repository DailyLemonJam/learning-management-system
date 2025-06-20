package com.leverx.learningmanagementsystem.multitenancy.database.config;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "spring.datasource")
@Component
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DatabaseProperties {

    private String url;

    private String username;

    private String password;

    private String driverClassName;
}
