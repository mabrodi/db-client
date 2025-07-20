package org.dimchik.query;

import org.dimchik.db.DatabaseConnector;
import org.dimchik.report.TablePrinter;

import java.sql.*;
import java.util.*;

public class QueryExecutor {
    private final DatabaseConnector connector;

    public QueryExecutor(DatabaseConnector connector) {
        this.connector = connector;
    }

    public void executeQuery(String sql) throws SQLException {
        if (!connector.isConnected())
            throw new SQLException("No active database connection");

        try (Statement statement = connector.getConnection().createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            ResultSetMetaData meta = resultSet.getMetaData();
            int cols = meta.getColumnCount();
            List<String[]> rows = new ArrayList<>();
            String[] headers = new String[cols];
            int[] maxWidths = new int[cols];

            // header
            for (int i = 1; i <= cols; i++) {
                headers[i - 1] = meta.getColumnName(i);
                maxWidths[i - 1] = headers[i - 1].length();
            }

            // data
            while (resultSet.next()) {
                String[] row = new String[cols];
                for (int i = 1; i <= cols; i++) {
                    String value = resultSet.getString(i);
                    row[i - 1] = value != null ? value : "NULL";
                    if (row[i - 1].length() > maxWidths[i - 1])
                        maxWidths[i - 1] = row[i - 1].length();
                }
                rows.add(row);
            }

            TablePrinter.printTable(headers, rows, maxWidths);

        } catch (SQLException e) {
            System.err.println("Query error: " + e.getMessage());
            throw e;
        }
    }


    public int executeUpdate(String sql) throws SQLException {
        if (!connector.isConnected())
            throw new SQLException("No active database connection");
        try (Statement statement = connector.getConnection().createStatement()) {
            return statement.executeUpdate(sql);
        }
    }
}
