package com.leverx.learningmanagementsystem.multitenancy.database.connectionprovider;

import com.leverx.learningmanagementsystem.multitenancy.servicemanager.dto.binding.BindingResponseDto;
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
@Profile("cloud")
@RequiredArgsConstructor
public class CloudDataSourceBasedMultiTenantConnectionProviderImpl extends AbstractDataSourceBasedMultiTenantConnectionProviderImpl<String>
    implements DisposableBean {

    private static final Integer DATASOURCE_MAX_POOLSIZE = 10;

    private final Map<String, DataSource> dataSources = new ConcurrentHashMap<>();
    private final DataSource defaultDataSource;

    @Override
    protected DataSource selectAnyDataSource() {
        log.info("SelectAnyDataSource was called");
        return defaultDataSource;
    }

    @Override
    protected DataSource selectDataSource(String currentTenantId) {
        log.info("SelectDataSource returned {}", currentTenantId);

        return dataSources.get(currentTenantId);
    }

    public void createTenantDataSource(BindingResponseDto binding, String tenantId) {
        var credentials = binding.credentials();
        var url = credentials.url();
        var username = credentials.user();
        var password = credentials.password();
        var driver = credentials.driver();

        var dataSource = new HikariDataSource();
        dataSource.setJdbcUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        dataSource.setDriverClassName(driver);
        dataSource.setMaximumPoolSize(DATASOURCE_MAX_POOLSIZE);

        dataSources.put(tenantId, dataSource);
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
