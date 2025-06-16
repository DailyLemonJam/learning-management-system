package com.leverx.learningmanagementsystem.multitenancy.database.tenantresolver;

import com.leverx.learningmanagementsystem.multitenancy.tenant.context.TenantContext;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.context.spi.CurrentTenantIdentifierResolver;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CurrentTenantIdentifierResolverImpl implements CurrentTenantIdentifierResolver<String> {

    @Override
    public String resolveCurrentTenantIdentifier() {
        log.info("Returning current tenant (resolver): %s%n", TenantContext.getTenantId());

        return TenantContext.getTenantId() == null ? "public" : TenantContext.getTenantId();
    }

    @Override
    public boolean validateExistingCurrentSessions() {
        return false;
    }
}