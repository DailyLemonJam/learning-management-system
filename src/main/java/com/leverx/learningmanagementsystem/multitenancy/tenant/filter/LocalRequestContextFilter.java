package com.leverx.learningmanagementsystem.multitenancy.tenant.filter;

import com.leverx.learningmanagementsystem.multitenancy.tenant.context.RequestContext;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import static java.util.Objects.isNull;

@Slf4j
@Profile("local")
@Component
public class LocalRequestContextFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, java.io.IOException {

        String tenantId = request.getHeader("X-Tenant-ID");
        if (isNull(tenantId)) {
            throw new RuntimeException("X-Tenant-ID is null");
        }

        tenantId = tenantId.replace("-", "_");

        log.info("TenantContext TenantId: {}", tenantId);

        RequestContext.setTenantId(tenantId);
        RequestContext.setSubdomain("current-domain-example");
        RequestContext.setUsername("current-user-example");

        try {
            filterChain.doFilter(request, response);
        } finally {
            RequestContext.clear();
        }
    }
}
