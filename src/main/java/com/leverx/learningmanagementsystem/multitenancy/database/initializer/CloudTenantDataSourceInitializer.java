package com.leverx.learningmanagementsystem.multitenancy.database.initializer;

import com.leverx.learningmanagementsystem.multitenancy.servicemanager.service.ServiceManager;
import com.leverx.learningmanagementsystem.multitenancy.database.connectionprovider.CloudDataSourceBasedMultiTenantConnectionProviderImpl;
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
        log.info("Running Liquibase migrator on all schemas");

        var schemaBindings = serviceManager.getAllBindings();

        log.info("SchemaBindings: %s".formatted(schemaBindings.toString()));

        for (var binding : schemaBindings.items()) {
            var tenantId = binding.labels().get("tenantId").getFirst();

            log.info("SchemaBinding: connected tenantId: %s".formatted(tenantId));

            connectionProvider.createTenantDataSource(binding, tenantId);

            log.info("Created DataSource for schema with tenantId: %s".formatted(tenantId));

            schemaMigrationService.applyLiquibaseChangelog(tenantId);

            log.info("Applied migration for schema with tenantId: %s".formatted(tenantId));
        }
    }
}
