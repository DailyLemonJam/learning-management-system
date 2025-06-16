package com.leverx.learningmanagementsystem.multitenancy.subscription;

import com.leverx.learningmanagementsystem.multitenancy.database.connectionprovider.LocalDataSourceBasedMultiTenantConnectionProviderImpl;
import liquibase.Liquibase;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.LiquibaseException;
import liquibase.resource.ClassLoaderResourceAccessor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.SQLException;

@Slf4j
@Profile("local")
@Service
@RequiredArgsConstructor
public class LocalTenantSchemaProvisioner implements TenantSchemaProvisioner {

    private final LocalDataSourceBasedMultiTenantConnectionProviderImpl multiTenantConnectionProvider;
    private final JdbcTemplate jdbcTemplate;

    @Override
    public void createSchema(String tenantId) {
        tenantId = createValidSQLTenantId(tenantId);
        try {
            initSchema(tenantId);
            createTenantDataSource(tenantId);
            applyLiquibaseChangelog(tenantId);
        } catch (Exception e) {
            deleteSchema(tenantId);
            throw e;
        }
    }

    @Override
    public void deleteSchema(String tenantId) {
        tenantId = createValidSQLTenantId(tenantId);
        try {
            dropSchema(tenantId);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw e;
        }
    }

    private void initSchema(String tenantId) {
        String createSchemaSQL = "CREATE SCHEMA IF NOT EXISTS %s".formatted(tenantId);
        jdbcTemplate.execute(createSchemaSQL);
    }

    private void createTenantDataSource(String tenantId) {
        multiTenantConnectionProvider.createAndConfigureTenantDataSource(tenantId);
    }

    private void dropSchema(String tenantId) {
        String createSchemaSQL = "DROP SCHEMA IF EXISTS %s CASCADE".formatted(tenantId);
        jdbcTemplate.execute(createSchemaSQL);
    }

    private void applyLiquibaseChangelog(String tenantId) {
        try (var connection = multiTenantConnectionProvider.getConnection(tenantId)) {
            String changelogPath = "/db/db.changelog-master.yaml";

            var liquibase = new Liquibase(changelogPath, new ClassLoaderResourceAccessor(),
                    new JdbcConnection(connection));
            liquibase.update("");

        } catch (SQLException | LiquibaseException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    private String createValidSQLTenantId(String tenantId) {
        return tenantId.replace("-", "_");
    }
}
