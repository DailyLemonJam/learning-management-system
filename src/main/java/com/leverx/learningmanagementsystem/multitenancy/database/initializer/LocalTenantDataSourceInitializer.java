package com.leverx.learningmanagementsystem.multitenancy.database.initializer;

import com.leverx.learningmanagementsystem.multitenancy.database.connectionprovider.LocalDataSourceBasedMultiTenantConnectionProviderImpl;
import liquibase.Liquibase;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.LiquibaseException;
import liquibase.resource.ClassLoaderResourceAccessor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.List;

@Slf4j
@Component
@Profile("local")
@RequiredArgsConstructor
public class LocalTenantDataSourceInitializer implements ApplicationRunner {

    private final LocalDataSourceBasedMultiTenantConnectionProviderImpl connectionProvider;
    private final JdbcTemplate jdbcTemplate;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        var schemas = getAllSchemas();

        log.info(schemas.toString());

        for (var schema : schemas) {
            createDataSourceForTenant(schema);
            applyLiquibaseForTenant(schema);
        }
    }

    private List<String> getAllSchemas() {
        String query = "SELECT schema_name " +
                "FROM information_schema.schemata " +
                "WHERE schema_name NOT IN ('pg_catalog', 'information_schema', 'pg_toast')";
        return jdbcTemplate.queryForList(query, String.class);
    }

    private void createDataSourceForTenant(String schema) {
        connectionProvider.createAndConfigureTenantDataSource(schema);
    }

    private void applyLiquibaseForTenant(String schema) throws Exception {
        try (var connection = connectionProvider.getConnection(schema)) {
            String changelogPath = "/db/db.changelog-master.yaml";

            var liquibase = new Liquibase(changelogPath, new ClassLoaderResourceAccessor(),
                    new JdbcConnection(connection));
            liquibase.update("");

        } catch (SQLException | LiquibaseException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
