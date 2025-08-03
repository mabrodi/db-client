package org.dimchik.config;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class FileDatabaseConfig implements DatabaseConfig {

    private final Properties properties = new Properties();

    public FileDatabaseConfig(String fileName) {
        try (FileInputStream fileInputStream = new FileInputStream(fileName)) {
            properties.load(fileInputStream);
        } catch (IOException e) {
            System.err.println("Failed to load " + fileName);
        }
    }

    @Override
    public String url() {
        return properties.getProperty("database.url");
    }

    @Override
    public String userName() {
        return properties.getProperty("database.username");
    }

    @Override
    public String password() {
        return properties.getProperty("database.password");
    }
}
