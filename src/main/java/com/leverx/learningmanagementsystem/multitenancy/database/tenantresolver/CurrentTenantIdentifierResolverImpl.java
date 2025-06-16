package com.leverx.learningmanagementsystem.multitenancy.database.tenantresolver;

import com.leverx.learningmanagementsystem.multitenancy.tenant.context.TenantContext;
import org.hibernate.context.spi.CurrentTenantIdentifierResolver;
import org.springframework.stereotype.Component;

@Component
public class CurrentTenantIdentifierResolverImpl implements CurrentTenantIdentifierResolver<String> {

    @Override
    public String resolveCurrentTenantIdentifier() {
        System.out.printf("Returning current tenant (resolver): %s%n", TenantContext.getTenantId());
        return TenantContext.getTenantId() == null ? "public" : TenantContext.getTenantId();
    }

    @Override
    public boolean validateExistingCurrentSessions() {
        return false;
    }
}