package com.leverx.learningmanagementsystem.multitenancy.database.tenantresolver;

import com.leverx.learningmanagementsystem.multitenancy.tenant.context.RequestContext;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.context.spi.CurrentTenantIdentifierResolver;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CurrentTenantIdentifierResolverImpl implements CurrentTenantIdentifierResolver<String> {

    @Override
    public String resolveCurrentTenantIdentifier() {
        log.info("Returning current tenant (resolver): {}", RequestContext.getTenantId());

        return RequestContext.getTenantId() == null ? "public" : RequestContext.getTenantId();
    }

    @Override
    public boolean validateExistingCurrentSessions() {
        return false;
    }
}