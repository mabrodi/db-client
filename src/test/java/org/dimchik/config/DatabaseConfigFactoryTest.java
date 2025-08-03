package org.dimchik.config;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DatabaseConfigFactoryTest {
    @Test
    void shouldReturnCommandLineConfigIfAllParamsPresent() {
        String[] args = {
                "-url=jdbc:postgresql://localhost:5432/testdb",
                "-user=testuser",
                "-password=secret"
        };

        DatabaseConfig config = DatabaseConfigFactory.getReader(args);

        assertInstanceOf(CommandLineDatabaseConfig.class, config);
    }

    @Test
    void shouldReturnFileConfigIfSomeParamsMissing() {
        String[] args = {
                "-url=jdbc:postgresql://localhost:5432/testdb"
        };

        DatabaseConfig config = DatabaseConfigFactory.getReader(args);

        assertInstanceOf(FileDatabaseConfig.class, config);
    }
}