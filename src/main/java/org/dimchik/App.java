package org.dimchik;

import org.dimchik.config.DatabaseConfig;
import org.dimchik.config.DatabaseConfigFactory;
import org.postgresql.ds.PGSimpleDataSource;

import javax.sql.DataSource;

public class App {
    public static void main(String[] args) {
        DatabaseConfig config = DatabaseConfigFactory.getReader(args);
        DataSource dataSource = createDataSource(config);

        ApplicationRunner applicationRunner = new ApplicationRunner(config, dataSource);
        applicationRunner.run();
    }

    private static DataSource createDataSource(DatabaseConfig config) {
        PGSimpleDataSource ds = new PGSimpleDataSource();
        ds.setURL(config.url());
        ds.setUser(config.userName());
        ds.setPassword(config.password());
        return ds;
    }
}