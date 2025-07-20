package org.dimchik.report;

import java.util.List;

public class TablePrinter {
    public static void printTable(String[] headers, List<String[]> rows, int[] colWidths) {
        printBorder(colWidths);
        printRow(headers, colWidths);
        printBorder(colWidths);
        for (String[] row : rows) printRow(row, colWidths);
        printBorder(colWidths);
        System.out.println(rows.size() + " rows returned");
    }

    private static void printRow(String[] row, int[] colWidths) {
        System.out.print("|");
        for (int i = 0; i < row.length; i++)
            System.out.printf(" %-" + colWidths[i] + "s |", row[i]);
        System.out.println();
    }

    private static void printBorder(int[] colWidths) {
        System.out.print("+");
        for (int width : colWidths)
            System.out.print("-".repeat(width + 2) + "+");
        System.out.println();
    }
}
