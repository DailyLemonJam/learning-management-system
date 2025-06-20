package com.leverx.learningmanagementsystem.multitenancy.database.tenantresolver;

import com.leverx.learningmanagementsystem.multitenancy.tenant.context.RequestContext;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.context.spi.CurrentTenantIdentifierResolver;
import org.springframework.stereotype.Component;

import static java.util.Objects.isNull;

@Slf4j
@Component
public class CurrentTenantIdentifierResolverImpl implements CurrentTenantIdentifierResolver<String> {

    @Override
    public String resolveCurrentTenantIdentifier() {
        log.info("Returning current tenant (resolver): {}", RequestContext.getTenantId());

        return isNull(RequestContext.getTenantId()) ? "public" : RequestContext.getTenantId();
    }

    @Override
    public boolean validateExistingCurrentSessions() {
        return false;
    }
}