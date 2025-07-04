package com.leverx.learningmanagementsystem.multitenancy.database.migration;

import com.leverx.learningmanagementsystem.multitenancy.database.datasource.provider.CloudDataSourceBasedMultiTenantConnectionProviderImpl;
import liquibase.exception.LiquibaseException;
import liquibase.integration.spring.SpringLiquibase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@Profile("cloud")
@RequiredArgsConstructor
public class CloudLiquibaseSchemaMigrationService implements LiquibaseSchemaMigrationService {

    private static final String CHANGE_LOG_PATH = "/db/db.changelog-master.yaml";

    private final CloudDataSourceBasedMultiTenantConnectionProviderImpl connectionProvider;

    @Override
    public void applyChangelog(String tenantId) {
        try {
            var dataSource = connectionProvider.getDataSourceByTenantId(tenantId);

            var liquibase = new SpringLiquibase();
            liquibase.setDataSource(dataSource);
            liquibase.setChangeLog(CHANGE_LOG_PATH);
            liquibase.afterPropertiesSet();

        } catch (LiquibaseException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
