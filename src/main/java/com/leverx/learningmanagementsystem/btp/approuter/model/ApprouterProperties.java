package com.leverx.learningmanagementsystem.btp.approuter.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "user-approuter-settings")
@Component
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ApprouterProperties {

    private String approuterName;
}
