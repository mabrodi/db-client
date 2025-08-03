package org.dimchik.config;

public class DatabaseConfigFactory {
    public static DatabaseConfig getReader(String[] args) {
        DatabaseConfig reader;

        reader = new CommandLineDatabaseConfig(args);
        if (reader.url() != null && reader.userName() != null && reader.password() != null) {
            return reader;
        }

        return new FileDatabaseConfig("application.properties");
    }
}
