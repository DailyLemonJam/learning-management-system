package com.leverx.learningmanagementsystem.multitenancy.database.initializer;

import com.leverx.learningmanagementsystem.btp.servicemanager.service.ServiceManager;
import com.leverx.learningmanagementsystem.multitenancy.database.connection.provider.CloudDataSourceBasedMultiTenantConnectionProviderImpl;
import com.leverx.learningmanagementsystem.multitenancy.database.migration.LiquibaseSchemaMigrationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@Profile("cloud")
@RequiredArgsConstructor
public class CloudTenantDataSourceInitializer implements ApplicationRunner {

    private final CloudDataSourceBasedMultiTenantConnectionProviderImpl connectionProvider;
    private final LiquibaseSchemaMigrationService schemaMigrationService;
    private final ServiceManager serviceManager;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        initializeSchemas();
    }

    private void initializeSchemas() {
        var schemaBindings = serviceManager.getAllBindings();

        for (var binding : schemaBindings.items()) {
            var tenantId = binding.labels().get("tenantId").getFirst();

            connectionProvider.createTenantDataSource(binding, tenantId);

            schemaMigrationService.applyChangelog(tenantId);
        }
    }
}
