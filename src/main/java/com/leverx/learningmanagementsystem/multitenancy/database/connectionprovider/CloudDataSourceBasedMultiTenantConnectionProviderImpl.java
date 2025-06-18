package com.leverx.learningmanagementsystem.multitenancy.database.connectionprovider;

import com.leverx.learningmanagementsystem.btp.servicemanager.dto.binding.BindingResponseDto;
import com.leverx.learningmanagementsystem.multitenancy.database.config.DatabaseProperties;
import com.zaxxer.hikari.HikariDataSource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.engine.jdbc.connections.spi.AbstractDataSourceBasedMultiTenantConnectionProviderImpl;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Component
@Profile("cloud")
@RequiredArgsConstructor
public class CloudDataSourceBasedMultiTenantConnectionProviderImpl extends AbstractDataSourceBasedMultiTenantConnectionProviderImpl<String> {

    private final Map<String, DataSource> dataSources = new ConcurrentHashMap<>();
    private final DatabaseProperties databaseProperties;
    private final DataSource defaultDataSource;

    @Override
    protected DataSource selectAnyDataSource() {
        log.info("SelectAnyDataSource was called");
        return defaultDataSource;
    }

    @Override
    protected DataSource selectDataSource(String currentTenantId) {
        log.info("SelectDataSource returned %s".formatted(currentTenantId));

        return dataSources.get(currentTenantId);
    }

    public void createTenantDataSource(BindingResponseDto binding, String tenantId) {
        var credentials = binding.credentials();
        var url = credentials.get("url");
        var username = credentials.get("user");
        var password = credentials.get("password");
        var driver = credentials.get("driver");

        var dataSource = new HikariDataSource();
        dataSource.setJdbcUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        dataSource.setDriverClassName(driver);
        dataSource.setMaximumPoolSize(10);

        dataSources.put(tenantId, dataSource);
    }

    public void deleteTenantDataSource(String tenantId) {
        dataSources.remove(tenantId);
    }
}
