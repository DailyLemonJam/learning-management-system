package com.leverx.learningmanagementsystem.multitenancy.tenant.filter;

import com.auth0.jwt.JWT;
import com.leverx.learningmanagementsystem.multitenancy.tenant.context.TenantContext;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Profile("cloud")
@Component
public class CloudTenantFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        if (request.getRequestURI().startsWith("/actuator")) {
            filterChain.doFilter(request, response);
            return;
        }

        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            var decodedJWT = JWT.decode(token);

            String zid = decodedJWT.getClaim("zid").asString();
            var extAttr = decodedJWT.getClaim("ext_attr").asMap();
            String subdomain = extAttr != null ? (String) extAttr.get("subdomain") : null;

            TenantContext.setTenant(zid, subdomain);
        }

        try {
            filterChain.doFilter(request, response);
        } finally {
            TenantContext.clear();
        }
    }
}
