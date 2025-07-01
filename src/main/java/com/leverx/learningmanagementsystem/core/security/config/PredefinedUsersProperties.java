package com.leverx.learningmanagementsystem.core.security.config;

import com.leverx.learningmanagementsystem.core.security.model.PredefinedUser;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "security.configuration")
@Component
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PredefinedUsersProperties {

    private PredefinedUser defaultUser;

    private PredefinedUser manager;
}
