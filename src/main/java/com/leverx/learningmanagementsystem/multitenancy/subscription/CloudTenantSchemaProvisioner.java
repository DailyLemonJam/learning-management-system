package com.leverx.learningmanagementsystem.multitenancy.subscription;

import com.leverx.learningmanagementsystem.btp.servicemanager.service.ServiceManager;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Profile("cloud")
@Service
@RequiredArgsConstructor
public class CloudTenantSchemaProvisioner implements TenantSchemaProvisioner {

    private final ServiceManager serviceManager;

    @Override
    public void createSchema(String tenantId) {
        //serviceManager.createInstance()

        //serviceManager.createBinding()

        // TODO: use CloudDataSourceBasedMultiTenantConnectionProviderImpl
        // to create DataSource
    }

    @Override
    public void deleteSchema(String tenantId) {
        //serviceManager.deleteBinding();

        //serviceManager.deleteInstance();

        // TODO: use CloudDataSourceBasedMultiTenantConnectionProviderImpl
        // to delete DataSource
    }
}
