package org.dimchik.dao.mapper;

import org.dimchik.entity.SqlQueryResult;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ExecutorRowMapper {
    public static SqlQueryResult fromResultSet(ResultSet rs) throws SQLException {
        ResultSetMetaData meta = rs.getMetaData();
        int cols = meta.getColumnCount();
        List<String[]> rows = new ArrayList<>();
        String[] headers = new String[cols];
        int[] maxWidths = new int[cols];

        for (int i = 1; i <= cols; i++) {
            headers[i - 1] = meta.getColumnName(i);
            maxWidths[i - 1] = headers[i - 1].length();
        }

        while (rs.next()) {
            String[] row = new String[cols];
            for (int i = 1; i <= cols; i++) {
                String value = rs.getString(i);
                row[i - 1] = value != null ? value : "NULL";
                maxWidths[i - 1] = Math.max(maxWidths[i - 1], row[i - 1].length());
            }
            rows.add(row);
        }

        return new SqlQueryResult(headers, rows, maxWidths);
    }
}
