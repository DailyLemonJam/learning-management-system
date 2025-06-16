package com.leverx.learningmanagementsystem.multitenancy.tenant.filter;

import com.leverx.learningmanagementsystem.multitenancy.tenant.context.TenantContext;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Slf4j
@Profile("local")
@Component
public class LocalTenantFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, java.io.IOException {

        String tenantId = request.getHeader("X-Tenant-ID");
        if (tenantId == null) {
            throw new RuntimeException("X-Tenant-ID is null");
        }

        tenantId = tenantId.replace("-", "_");

        log.info("TenantContext TenantId: %s".formatted(tenantId));

        TenantContext.setTenant(tenantId, "optional-subdomain-here");

        try {
            filterChain.doFilter(request, response);
        } finally {
            TenantContext.clear();
        }
    }
}