package org.dimchik.renderer;

import org.dimchik.entity.SqlQueryResult;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ConsoleTableRendererTest {
    @Test
    void shouldRenderFormattedTableAsString() {
        String[] headers = {"ID", "Name"};
        List<String[]> rows = List.of(
                new String[]{"1", "Alice"},
                new String[]{"22", "Bob"}
        );
        int[] columnWidths = {2, 5};

        SqlQueryResult result = new SqlQueryResult(headers, rows, columnWidths);

        String output = ConsoleTableRenderer.renderTable(result);

        assertTrue(output.contains("| ID | Name  |"));
        assertTrue(output.contains("| 1  | Alice |"));
        assertTrue(output.contains("| 22 | Bob   |"));
        assertTrue(output.contains("2 rows returned"));

        assertTrue(output.contains("+----+-------+"));
    }

}