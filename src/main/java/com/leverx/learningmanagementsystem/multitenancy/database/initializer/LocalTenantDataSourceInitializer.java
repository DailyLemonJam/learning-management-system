package com.leverx.learningmanagementsystem.multitenancy.database.initializer;

import com.leverx.learningmanagementsystem.multitenancy.database.datasource.manager.LocalDataSourceManager;
import com.leverx.learningmanagementsystem.multitenancy.database.migration.LiquibaseSchemaMigrationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@Profile("local")
@RequiredArgsConstructor
public class LocalTenantDataSourceInitializer implements ApplicationRunner {

    private final LocalDataSourceManager localDataSourceManager;
    private final LiquibaseSchemaMigrationService schemaMigrationService;
    private final JdbcTemplate jdbcTemplate;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        initializeSchemas();
    }

    private void initializeSchemas() throws Exception {
        var schemas = getAllSchemas();

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
        localDataSourceManager.createTenantDataSource(schema);
    }

    private void applyLiquibaseForTenant(String schema) throws Exception {
        schemaMigrationService.applyChangelog(schema);
    }
}
