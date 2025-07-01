package com.leverx.learningmanagementsystem.multitenancy.database.connectionprovider;

import com.leverx.learningmanagementsystem.multitenancy.database.config.DatabaseProperties;
import com.zaxxer.hikari.HikariDataSource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.engine.jdbc.connections.spi.AbstractDataSourceBasedMultiTenantConnectionProviderImpl;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Component
@Profile("local")
@RequiredArgsConstructor
public class LocalDataSourceBasedMultiTenantConnectionProviderImpl extends AbstractDataSourceBasedMultiTenantConnectionProviderImpl<String>
        implements DisposableBean {

    private final Map<String, DataSource> dataSources = new ConcurrentHashMap<>();
    private final DatabaseProperties databaseProperties;
    private final DataSource defaultDataSource;

    @Override
    protected DataSource selectAnyDataSource() {
        return defaultDataSource;
    }

    @Override
    protected DataSource selectDataSource(String currentTenantId) {
        log.info("SelectDataSource returned {}", currentTenantId);

        return dataSources.get(currentTenantId);
    }

    public DataSource createTenantDataSource(String tenantId) {
        var dataSource = new HikariDataSource();
        dataSource.setJdbcUrl(databaseProperties.getUrl() + "?currentSchema=" + tenantId);
        dataSource.setUsername(databaseProperties.getUsername());
        dataSource.setPassword(databaseProperties.getPassword());
        dataSource.setMaximumPoolSize(10);

        dataSources.put(tenantId, dataSource);

        return dataSource;
    }

    public void deleteTenantDataSource(String tenantId) {
        dataSources.remove(tenantId);
    }

    @Override
    public void destroy() throws Exception {
        var openDataSources = dataSources.values();

        openDataSources.forEach(datasource ->
                ((HikariDataSource) datasource).close());

        dataSources.clear();
    }
}
