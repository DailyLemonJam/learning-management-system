package com.leverx.learningmanagementsystem.core.security.config;

import com.leverx.learningmanagementsystem.core.security.role.Role;
import com.sap.cloud.security.xsuaa.XsuaaServiceConfiguration;
import com.sap.cloud.security.xsuaa.token.TokenAuthenticationConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@EnableWebSecurity
@Profile("cloud")
@RequiredArgsConstructor
public class CloudSecurityConfiguration {

    private final XsuaaServiceConfiguration xsuaaServiceConfiguration;

    @Value("${security.configuration.default-user.username}")
    private String defaultUserUsername;

    @Value("${security.configuration.default-user.password}")
    private String defaultUserPassword;

    @Value("${security.configuration.manager-user.username}")
    private String managerUserUsername;

    @Value("${security.configuration.manager-user.password}")
    private String managerUserPassword;

    @Bean
    public UserDetailsService userDetailsService() {
        var defaultUser = createDefaultUser();
        var manager = createManagerUser();
        return new InMemoryUserDetailsManager(defaultUser, manager);
    }

    @Bean
    @Order(1)
    public SecurityFilterChain actuatorSecurityFilterChain(HttpSecurity http) throws Exception {
        return http
                .securityMatcher("/actuator/**")
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(configurer ->
                        configurer.sessionCreationPolicy(STATELESS))
                .authorizeHttpRequests(auth -> {
                    auth.requestMatchers("/actuator/health").permitAll();
                    auth.anyRequest().hasRole(Role.MANAGER.getValue());
                })
                .httpBasic(withDefaults())
                .build();
    }

    @Bean
    @Order(2)
    public SecurityFilterChain apiSecurityFilterChain(HttpSecurity http) throws Exception {
        return http
                .securityMatcher("/**")
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(configurer ->
                        configurer.sessionCreationPolicy(STATELESS))
                .authorizeHttpRequests(auth ->
                        auth.anyRequest().authenticated())
                .oauth2ResourceServer(oauth2 ->
                        oauth2.jwt(jwt ->
                                jwt.jwtAuthenticationConverter(new TokenAuthenticationConverter(xsuaaServiceConfiguration))))
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    private UserDetails createDefaultUser() {
        return User.builder()
                .username(defaultUserUsername)
                .password(passwordEncoder().encode(defaultUserPassword))
                .roles(Role.USER.getValue())
                .build();
    }

    private UserDetails createManagerUser() {
        return User.builder()
                .username(managerUserUsername)
                .password(passwordEncoder().encode(managerUserPassword))
                .roles(Role.MANAGER.getValue())
                .build();
    }
}
