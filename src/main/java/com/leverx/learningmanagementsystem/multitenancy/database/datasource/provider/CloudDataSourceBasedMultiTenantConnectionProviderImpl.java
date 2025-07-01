package com.leverx.learningmanagementsystem.multitenancy.database.datasource.provider;

import com.leverx.learningmanagementsystem.multitenancy.database.datasource.manager.CloudDataSourceManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.engine.jdbc.connections.spi.AbstractDataSourceBasedMultiTenantConnectionProviderImpl;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

@Slf4j
@Component
@Profile("cloud")
@RequiredArgsConstructor
public class CloudDataSourceBasedMultiTenantConnectionProviderImpl extends AbstractDataSourceBasedMultiTenantConnectionProviderImpl<String> {

    private final CloudDataSourceManager cloudDataSourceManager;

    public DataSource getDefaultDataSource() {
        return cloudDataSourceManager.getDefaultDataSource();
    }

    public DataSource getDataSourceByTenantId(String tenantId) {
        return selectDataSource(tenantId);
    }

    @Override
    protected DataSource selectAnyDataSource() {
        return cloudDataSourceManager.getDefaultDataSource();
    }

    @Override
    protected DataSource selectDataSource(String currentTenantId) {
        return cloudDataSourceManager.getDataSourceByTenantId(currentTenantId);
    }
}
