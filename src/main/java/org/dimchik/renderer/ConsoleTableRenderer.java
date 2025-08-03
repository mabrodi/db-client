package org.dimchik.renderer;

import org.dimchik.entity.SqlQueryResult;

public class ConsoleTableRenderer {
    public static String renderTable(SqlQueryResult sqlQueryResult) {
        StringBuilder sb = new StringBuilder();

        sb.append(renderBorder(sqlQueryResult.columnWidths()));
        sb.append(renderRow(sqlQueryResult.headers(), sqlQueryResult.columnWidths()));
        sb.append(renderBorder(sqlQueryResult.columnWidths()));

        for (String[] row : sqlQueryResult.rows()) {
            sb.append(renderRow(row, sqlQueryResult.columnWidths()));
        }

        sb.append(renderBorder(sqlQueryResult.columnWidths()));
        sb.append(sqlQueryResult.rows().size()).append(" rows returned\n");

        return sb.toString();
    }

    private static String renderRow(String[] row, int[] colWidths) {
        StringBuilder sb = new StringBuilder();
        sb.append("|");
        for (int i = 0; i < row.length; i++) {
            sb.append(String.format(" %-" + colWidths[i] + "s |", row[i]));
        }
        sb.append("\n");
        return sb.toString();
    }

    private static String renderBorder(int[] colWidths) {
        StringBuilder sb = new StringBuilder();
        sb.append("+");
        for (int width : colWidths) {
            sb.append("-".repeat(width + 2)).append("+");
        }
        sb.append("\n");
        return sb.toString();
    }
}
