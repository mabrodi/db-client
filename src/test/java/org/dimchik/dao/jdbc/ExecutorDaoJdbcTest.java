package org.dimchik.dao.jdbc;

import org.dimchik.dao.ExecutorDao;
import org.dimchik.entity.SqlQueryResult;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.sql.DataSource;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ExecutorDaoJdbcTest {
    @Mock
    DataSource dataSource;
    @Mock
    Connection connection;
    @Mock
    Statement statement;
    @Mock
    ResultSet resultSet;
    @Mock
    ResultSetMetaData resultSetMetaData;

    @Test
    void executeQueryShouldReturnSqlQueryResult() throws Exception {
        when(dataSource.getConnection()).thenReturn(connection);
        when(connection.createStatement()).thenReturn(statement);
        when(statement.executeQuery(anyString())).thenReturn(resultSet);
        when(resultSet.getMetaData()).thenReturn(resultSetMetaData);

        when(resultSetMetaData.getColumnCount()).thenReturn(2);
        when(resultSetMetaData.getColumnName(1)).thenReturn("id");
        when(resultSetMetaData.getColumnName(2)).thenReturn("name");

        when(resultSet.next()).thenReturn(true, true, false);
        when(resultSet.getString(1)).thenReturn("1", "2");
        when(resultSet.getString(2)).thenReturn("Alice", null);

        ExecutorDao dao = new ExecutorDaoJdbc(dataSource);
        SqlQueryResult result = dao.executeQuery("SELECT * FROM users");

        assertNotNull(result);
        assertArrayEquals(new String[]{"id", "name"}, result.headers());
        assertEquals(2, result.rows().size());
        assertArrayEquals(new String[]{"1", "Alice"}, result.rows().get(0));
        assertArrayEquals(new String[]{"2", "NULL"}, result.rows().get(1));
        assertArrayEquals(new int[]{2, 5}, result.columnWidths());
    }

    @Test
    void executeUpdateShouldReturnUpdateCount() throws Exception {
        when(dataSource.getConnection()).thenReturn(connection);
        when(connection.createStatement()).thenReturn(statement);
        when(statement.executeUpdate("DELETE FROM test")).thenReturn(3);

        ExecutorDao dao = new ExecutorDaoJdbc(dataSource);

        int updated = dao.executeUpdate("DELETE FROM test");

        assertEquals(3, updated);
    }
}