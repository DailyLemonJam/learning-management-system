package com.leverx.learningmanagementsystem.multitenancy.database.connectionprovider;

import lombok.RequiredArgsConstructor;
import org.hibernate.engine.jdbc.connections.spi.AbstractDataSourceBasedMultiTenantConnectionProviderImpl;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

@Component
@Profile("cloud")
@RequiredArgsConstructor
public class CloudDataSourceBasedMultiTenantConnectionProviderImpl extends AbstractDataSourceBasedMultiTenantConnectionProviderImpl<String> {

    // TODO: check pc for local commits, mb implementation is still there

    @Override
    protected DataSource selectAnyDataSource() {
        return null;
    }

    @Override
    protected DataSource selectDataSource(String s) {
        return null;
    }

    public DataSource createTenantDataSource(String tenantId) {
        return null;
    }

    public void deleteTenantDataSource(String tenantId) {

    }
}
