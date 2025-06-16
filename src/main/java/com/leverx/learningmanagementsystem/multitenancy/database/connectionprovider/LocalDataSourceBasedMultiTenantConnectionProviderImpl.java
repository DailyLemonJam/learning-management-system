package com.leverx.learningmanagementsystem.multitenancy.database.connectionprovider;

import com.leverx.learningmanagementsystem.multitenancy.database.config.LocalDatabaseProperties;
import com.zaxxer.hikari.HikariDataSource;
import lombok.RequiredArgsConstructor;
import org.hibernate.engine.jdbc.connections.spi.AbstractDataSourceBasedMultiTenantConnectionProviderImpl;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
@Profile("local")
@RequiredArgsConstructor
public class LocalDataSourceBasedMultiTenantConnectionProviderImpl extends AbstractDataSourceBasedMultiTenantConnectionProviderImpl<String> {

    private final Map<String, DataSource> dataSources = new ConcurrentHashMap<>();
    private final LocalDatabaseProperties localDatabaseProperties;

    @Override
    protected DataSource selectAnyDataSource() {
        System.out.println("SelectAnyDataSource was called");
        return dataSources.values().iterator().next();
    }

    @Override
    protected DataSource selectDataSource(String currentTenantId) {
        System.out.println("SelectDataSource returned %s".formatted(currentTenantId));
        return dataSources.get(currentTenantId);
    }

    // TODO: init / refresh dataSources map

    public DataSource createAndConfigureTenantDataSource(String tenantId) {
        var dataSource = new HikariDataSource();
        dataSource.setJdbcUrl(localDatabaseProperties.getUrl() + "?currentSchema=" + tenantId);
        dataSource.setUsername(localDatabaseProperties.getUsername());
        dataSource.setPassword(localDatabaseProperties.getPassword());
        dataSource.setSchema(tenantId);
        dataSource.setMaximumPoolSize(10);

        dataSources.put(tenantId, dataSource);

        return dataSource;
    }
}
