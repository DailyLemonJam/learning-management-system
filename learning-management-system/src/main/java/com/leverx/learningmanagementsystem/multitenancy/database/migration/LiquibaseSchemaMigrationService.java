package com.leverx.learningmanagementsystem.multitenancy.database.migration;

public interface LiquibaseSchemaMigrationService {

    void applyLiquibaseChangelog(String schemaName);
}
