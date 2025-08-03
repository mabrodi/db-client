package org.dimchik;

import org.dimchik.config.DatabaseConfig;
import org.dimchik.dao.ExecutorDao;
import org.dimchik.dao.jdbc.ExecutorDaoJdbc;
import org.dimchik.service.ConsoleSqlExecutorService;
import org.dimchik.service.base.ConsoleSqlExecutorServiceBase;

import javax.sql.DataSource;

public class ApplicationRunner {
    private final DatabaseConfig config;
    private final DataSource dataSource;

    public ApplicationRunner(DatabaseConfig config, DataSource dataSource) {
        this.config = config;
        this.dataSource = dataSource;
    }

    public void run() {
        try {
            printConnectionInfo();
            ExecutorDao executor = new ExecutorDaoJdbc(dataSource);
            ConsoleSqlExecutorService cli = new ConsoleSqlExecutorServiceBase(executor);
            cli.runInteractiveShell();
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            printHelp();
        }
    }

    private void printConnectionInfo() {
        System.out.println("URL: " + config.url());
        System.out.println("User: " + config.userName());
        System.out.println("Password: " + config.password());
    }

    private void printHelp() {
        System.out.println("Usage:");
        System.out.println("java -jar db-client.jar -url=... -user=... -password=...");
        System.out.println("or set env variables: DB_URL, DB_USER, DB_PASSWORD");
        System.out.println("or provide 'application.properties' with keys: database.url, database.username, database.password");
    }
}
