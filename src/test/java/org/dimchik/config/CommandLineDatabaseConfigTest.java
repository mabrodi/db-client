package org.dimchik.config;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CommandLineDatabaseConfigTest {
    @Test
    void shouldReadDatabaseConfigFromCommandLineArgs() {
        String[] args = {
                "-url=jdbc:postgresql://localhost:5432/testdb",
                "-user=testuser",
                "-password=secret"
        };

        DatabaseConfig config = new CommandLineDatabaseConfig(args);

        assertEquals("jdbc:postgresql://localhost:5432/testdb", config.url());
        assertEquals("testuser", config.userName());
        assertEquals("secret", config.password());
    }

    @Test
    void shouldReturnNullIfKeyNotPresent() {
        String[] args = {"-url=some-url"};
        DatabaseConfig config = new CommandLineDatabaseConfig(args);

        assertNull(config.userName());
        assertNull(config.password());
    }

}