package com.leverx.learningmanagementsystem.multitenancy.subscription;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Profile("cloud")
@Service
public class CloudTenantSchemaProvisioner implements TenantSchemaProvisioner {

    @Override
    public void createSchema(String tenantId) {
        // via Service Manager
    }

    @Override
    public void deleteSchema(String tenantId) {
        // via Service Manager
    }
}
