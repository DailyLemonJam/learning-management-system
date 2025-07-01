package com.leverx.learningmanagementsystem.multitenancy.database.migration;

public interface LiquibaseSchemaMigrationService {

    void applyChangelog(String schemaName);
}
