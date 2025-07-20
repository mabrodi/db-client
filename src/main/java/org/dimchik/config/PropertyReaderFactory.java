package org.dimchik.config;

public class PropertyReaderFactory {
    public static PropertyReader getReader(String[] args) {
        PropertyReader reader;

        reader = new CommandLinePropertyReader(args);
        if (reader.url() != null && reader.userName() != null && reader.password() != null) {
            return reader;
        }

        return new FilePropertyReader("application.properties");
    }
}
