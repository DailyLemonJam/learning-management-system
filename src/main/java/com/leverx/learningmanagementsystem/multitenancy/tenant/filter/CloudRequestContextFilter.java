package com.leverx.learningmanagementsystem.multitenancy.tenant.filter;

import com.auth0.jwt.JWT;
import com.leverx.learningmanagementsystem.multitenancy.tenant.context.RequestContext;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@Profile("cloud")
@Component
public class CloudRequestContextFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        if (request.getRequestURI().startsWith("/actuator")) {
            filterChain.doFilter(request, response);
            return;
        }

        log.info("Getting zid and subdomain from jwt");

        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            var decodedJWT = JWT.decode(token);
            log.info("Decoded jwt: {}", decodedJWT.getPayload());

            String zid = decodedJWT.getClaim("zid").asString();
            log.info("zid: {}", zid);

            var extAttr = decodedJWT.getClaim("ext_attr").asMap();
            log.info("ext_attr: {}", extAttr);

            String subdomain = (String) extAttr.get("zdn");
            log.info("subdomain: {}", subdomain);

            RequestContext.setTenant(zid, subdomain);
        }

        try {
            filterChain.doFilter(request, response);
        } finally {
            RequestContext.clear();
        }
    }
}
