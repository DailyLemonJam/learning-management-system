package com.leverx.learningmanagementsystem.multitenancy.tenant.filter;

import com.leverx.learningmanagementsystem.multitenancy.tenant.context.RequestContext;
import com.sap.cloud.security.xsuaa.token.AuthenticationToken;
import com.sap.cloud.security.xsuaa.token.XsuaaToken;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.security.Principal;
import java.util.Optional;

import static lombok.AccessLevel.PRIVATE;

@Slf4j
@Profile("cloud")
public class CloudRequestContextFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        try {
            Optional<XsuaaToken> xsuaaTokenOpt = SecurityUtils.retrieveXsuaaTokenFromRequest(request);
            Optional<String> tenantUsernameOpt = xsuaaTokenOpt.map(XsuaaToken::getUsername);
            Optional<String> tenantIdOpt = xsuaaTokenOpt.map(XsuaaToken::getZoneId);
            Optional<String> subdomainOpt = xsuaaTokenOpt.map(XsuaaToken::getSubdomain);

            tenantUsernameOpt.ifPresent(RequestContext::setUsername);
            tenantIdOpt.ifPresent(RequestContext::setTenantId);
            subdomainOpt.ifPresent(RequestContext::setSubdomain);

            filterChain.doFilter(request, response);
        } finally {
            RequestContext.clear();
        }
    }
}

@NoArgsConstructor(access = PRIVATE)
final class SecurityUtils {

    public static Optional<XsuaaToken> retrieveXsuaaTokenFromRequest(HttpServletRequest request) {
        Optional<Principal> userPrincipalOpt = Optional.ofNullable(request.getUserPrincipal());

        return userPrincipalOpt.filter(it -> it instanceof AuthenticationToken)
                .map(it -> (XsuaaToken) ((AuthenticationToken) it).getPrincipal());
    }
}