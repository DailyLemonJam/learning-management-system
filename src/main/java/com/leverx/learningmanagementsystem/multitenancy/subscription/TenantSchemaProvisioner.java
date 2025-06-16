package com.leverx.learningmanagementsystem.multitenancy.subscription;

public interface TenantSchemaProvisioner {

    void createSchema(String tenantId);

    void deleteSchema(String tenantId);
}
