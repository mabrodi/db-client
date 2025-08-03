package org.dimchik.service.base;

import org.dimchik.dao.ExecutorDao;
import org.dimchik.entity.SqlQueryResult;
import org.dimchik.renderer.ConsoleTableRenderer;
import org.dimchik.service.ConsoleSqlExecutorService;

import java.sql.SQLException;
import java.util.Scanner;

public class ConsoleSqlExecutorServiceBase implements ConsoleSqlExecutorService {
    private final ExecutorDao executor;

    public ConsoleSqlExecutorServiceBase(ExecutorDao executor) {
        this.executor = executor;
    }

    @Override
    public void runInteractiveShell() {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("Enter SQL commands (type 'exit' to quit):");

            while (true) {
                System.out.print("SQL> ");
                String sql = scanner.nextLine().trim();

                if (sql.equalsIgnoreCase("exit")) break;
                if (sql.isEmpty()) continue;

                executeSql(sql);
            }
        }
    }

    private void executeSql(String sql) {
        try {
            if (sql.toUpperCase().startsWith("SELECT")) {
                SqlQueryResult result = executor.executeQuery(sql);
                System.out.println(ConsoleTableRenderer.renderTable(result));
            } else {
                int affected = executor.executeUpdate(sql);
                System.out.println("Query OK, " + affected + " rows affected");
            }
        } catch (SQLException e) {
            System.err.println("SQL Error: " + e.getMessage());
        }
    }
}
