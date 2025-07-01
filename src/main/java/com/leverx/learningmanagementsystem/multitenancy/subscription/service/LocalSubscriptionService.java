package com.leverx.learningmanagementsystem.multitenancy.subscription.service;

import com.leverx.learningmanagementsystem.multitenancy.database.datasource.manager.LocalDataSourceManager;
import com.leverx.learningmanagementsystem.multitenancy.database.migration.LiquibaseSchemaMigrationService;
import com.leverx.learningmanagementsystem.multitenancy.subscription.dto.DependenciesResponseDto;
import com.leverx.learningmanagementsystem.multitenancy.subscription.dto.SubscribeRequestDto;
import com.leverx.learningmanagementsystem.multitenancy.subscription.dto.UnsubscribeRequestDto;
import com.leverx.learningmanagementsystem.multitenancy.subscription.dto.UnsubscribeResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@Profile("local")
@RequiredArgsConstructor
public class LocalSubscriptionService implements SubscriptionService {

    private static final String LOCAL_TEST_TENANT_SPECIFIC_URL_TEMPLATE = "https://%s-dev-approuter.cfapps.us10-001.hana.ondemand.com";

    private final LocalDataSourceManager localDataSourceManager;
    private final LiquibaseSchemaMigrationService schemaMigrationService;
    private final JdbcTemplate jdbcTemplate;

    @Override
    public String subscribe(SubscribeRequestDto request) {
        var tenantId = createValidSQLTenantId(request.subscribedTenantId());

        try {
            createSchema(tenantId);
            createTenantDataSource(tenantId);
            applyLiquibaseChangelog(tenantId);
        } catch (Exception e) {
            deleteSchema(tenantId);
            deleteTenantDataSource(tenantId);
            throw e;
        }

        return LOCAL_TEST_TENANT_SPECIFIC_URL_TEMPLATE.formatted(request.subscribedSubdomain());
    }

    @Override
    public UnsubscribeResponseDto unsubscribe(UnsubscribeRequestDto request) {
        var tenantId = request.subscribedTenantId();

        try {
            deleteSchema(tenantId);
            deleteTenantDataSource(tenantId);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw e;
        }

        return new UnsubscribeResponseDto("Tenant successfully unsubscribed");
    }

    @Override
    public List<DependenciesResponseDto> getDependencies() {
        return List.of();
    }

    private void createSchema(String name) {
        String createSchemaSQL = "CREATE SCHEMA IF NOT EXISTS %s".formatted(name);
        jdbcTemplate.execute(createSchemaSQL);
    }

    private void deleteSchema(String name) {
        String deleteSchemaSQL = "DROP SCHEMA IF EXISTS %s CASCADE".formatted(name);
        jdbcTemplate.execute(deleteSchemaSQL);
    }

    private void createTenantDataSource(String tenantId) {
        localDataSourceManager.createTenantDataSource(tenantId);
    }

    private void deleteTenantDataSource(String tenantId) {
        localDataSourceManager.deleteTenantDataSource(tenantId);
    }

    private void applyLiquibaseChangelog(String tenantId) {
        schemaMigrationService.applyChangelog(tenantId);
    }

    private String createValidSQLTenantId(String tenantId) {
        return tenantId.replace("-", "_");
    }
}
