package org.dimchik;


import org.dimchik.config.PropertyReader;
import org.dimchik.config.PropertyReaderFactory;
import org.dimchik.db.DatabaseConnector;
import org.dimchik.query.QueryExecutor;

import java.sql.SQLException;
import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        DatabaseConnector connector = null;

        try {
            PropertyReader reader = PropertyReaderFactory.getReader(args);
            printConnectionInfo(reader);

            connector = new DatabaseConnector(
                    reader.url(),
                    reader.userName(),
                    reader.password()
            );

            connector.connect();

            QueryExecutor executor = new QueryExecutor(connector);
            runInteractiveMode(executor);

        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            printHelp();
        } finally {
            if (connector != null && connector.isConnected()) {
                connector.disconnect();
            }
        }

    }

    private static void printConnectionInfo(PropertyReader reader) {
        System.out.println("URL: " + reader.url());
        System.out.println("User: " + reader.userName());
        System.out.println("Password: " + reader.password());
    }

    private static void runInteractiveMode(QueryExecutor executor) {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("Enter SQL commands (type 'exit' to quit):");
            while (true) {
                System.out.print("SQL> ");
                String sql = scanner.nextLine().trim();

                if (sql.equalsIgnoreCase("exit")) break;
                if (sql.isEmpty()) continue;

                try {
                    if (sql.toUpperCase().startsWith("SELECT")) {
                        executor.executeQuery(sql);
                    } else {
                        int rowsAffected = executor.executeUpdate(sql);
                        System.out.println("Query OK, " + rowsAffected + " rows affected");
                    }
                } catch (SQLException e) {
                    System.err.println("SQL Error: " + e.getMessage());
                }
            }
        }
    }

    private static String getArg(String[] args, String key) {
        for (String arg : args) {
            if (arg.startsWith(key + "=")) {
                return arg.substring(key.length() + 1);
            }
        }
        return null;
    }

    private static void printHelp() {
        System.out.println("Usage:");
        System.out.println("  java -jar db-client.jar -url=jdbc:postgresql://localhost:5432/yourdb -user=postgres -password=123");
        System.out.println("  or set environment variables: DB_URL, DB_USER, DB_PASSWORD");
        System.out.println("  or put 'application.properties' with keys: database.url, database.username, database.password");
        System.out.println("Optional: -reports=PATH (directory to save HTML reports, default: ./reports)");
    }
}