package com.leverx.learningmanagementsystem.multitenancy.database.migration;

import liquibase.Liquibase;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.LiquibaseException;
import liquibase.resource.ClassLoaderResourceAccessor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.engine.jdbc.connections.spi.AbstractDataSourceBasedMultiTenantConnectionProviderImpl;
import org.hibernate.engine.jdbc.connections.spi.AbstractMultiTenantConnectionProvider;
import org.springframework.stereotype.Service;

import java.sql.SQLException;

@Slf4j
@Service
@RequiredArgsConstructor
public class LiquibaseSchemaMigrationServiceImpl implements LiquibaseSchemaMigrationService {

    private static final String CHANGE_LOG_PATH = "/db/db.changelog-master.yaml";

    private final AbstractDataSourceBasedMultiTenantConnectionProviderImpl<String> connectionProvider;

    @Override
    public void applyLiquibaseChangelog(String tenantId) {
        try {
            var connection = connectionProvider.getConnection(tenantId);

            var liquibase = new Liquibase(CHANGE_LOG_PATH, new ClassLoaderResourceAccessor(),
                    new JdbcConnection(connection));
            liquibase.update("");

        } catch (SQLException | LiquibaseException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
