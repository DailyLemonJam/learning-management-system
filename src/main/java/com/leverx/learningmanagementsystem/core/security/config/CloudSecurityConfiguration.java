package com.leverx.learningmanagementsystem.core.security.config;

import com.leverx.learningmanagementsystem.core.security.role.Role;
import com.sap.cloud.security.xsuaa.XsuaaServiceConfiguration;
import com.sap.cloud.security.xsuaa.token.TokenAuthenticationConverter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Slf4j
@Configuration
@EnableWebSecurity
@Profile("cloud")
@RequiredArgsConstructor
public class CloudSecurityConfiguration {

    private final XsuaaServiceConfiguration xsuaaServiceConfiguration;
    private final PredefinedUsersProperties predefinedUsersProperties;

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
                .authorizeHttpRequests(auth -> {
                    auth.requestMatchers("/api/v1/application-info").hasAuthority(Role.ADMIN.getValue());
                    auth.anyRequest().authenticated();
                })
                .oauth2ResourceServer(oauth2 ->
                        oauth2.jwt(jwt ->
                                jwt.jwtAuthenticationConverter(getJwtAuthoritiesConverter())))
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    private Converter<Jwt, AbstractAuthenticationToken> getJwtAuthoritiesConverter() {
        var converter = new TokenAuthenticationConverter(xsuaaServiceConfiguration);
        converter.setLocalScopeAsAuthorities(true);
        return converter;
    }

    private UserDetails createDefaultUser() {
        return User.builder()
                .username(predefinedUsersProperties.getDefaultUser().username())
                .password(passwordEncoder().encode(predefinedUsersProperties.getDefaultUser().password()))
                .roles(Role.USER.getValue())
                .build();
    }

    private UserDetails createManagerUser() {
        return User.builder()
                .username(predefinedUsersProperties.getManager().username())
                .password(passwordEncoder().encode(predefinedUsersProperties.getManager().password()))
                .roles(Role.MANAGER.getValue())
                .build();
    }
}
