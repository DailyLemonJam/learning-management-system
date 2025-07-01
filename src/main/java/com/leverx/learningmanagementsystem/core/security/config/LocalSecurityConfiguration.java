package com.leverx.learningmanagementsystem.core.security.config;

import com.leverx.learningmanagementsystem.core.security.role.Role;
import com.leverx.learningmanagementsystem.multitenancy.tenant.filter.LocalRequestContextFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
@Profile("local")
@RequiredArgsConstructor
public class LocalSecurityConfiguration {

    private final LocalRequestContextFilter localTenantFilter;
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
                        configurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
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
                        configurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth ->
                        auth.anyRequest().authenticated())
                .httpBasic(withDefaults())
                .addFilterAfter(localTenantFilter, BasicAuthenticationFilter.class)
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
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
