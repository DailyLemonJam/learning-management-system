package com.leverx.learningmanagementsystem.multitenancy.database.datasource.provider;

import com.leverx.learningmanagementsystem.multitenancy.database.datasource.manager.LocalDataSourceManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.engine.jdbc.connections.spi.AbstractDataSourceBasedMultiTenantConnectionProviderImpl;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

@Slf4j
@Component
@Profile("local")
@RequiredArgsConstructor
public class LocalDataSourceBasedMultiTenantConnectionProviderImpl extends AbstractDataSourceBasedMultiTenantConnectionProviderImpl<String> {

    private final LocalDataSourceManager localDataSourceManager;

    public DataSource getDefaultDataSource() {
        return localDataSourceManager.getDefaultDataSource();
    }

    public DataSource getDataSourceByTenantId(String tenantId) {
        return selectDataSource(tenantId);
    }

    @Override
    protected DataSource selectAnyDataSource() {
        return localDataSourceManager.getDefaultDataSource();
    }

    @Override
    protected DataSource selectDataSource(String currentTenantId) {
        return localDataSourceManager.getDataSourceByTenantId(currentTenantId);
    }
}
