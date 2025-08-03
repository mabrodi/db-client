package org.dimchik.dao.mapper;

import org.dimchik.entity.SqlQueryResult;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ExecutorRowMapperTest {
    @Mock
    ResultSet resultSet;
    @Mock
    ResultSetMetaData metaData;

    @Test
    void fromResultSet_ShouldMapCorrectly() throws Exception {
        when(resultSet.getMetaData()).thenReturn(metaData);
        when(metaData.getColumnCount()).thenReturn(2);
        when(metaData.getColumnName(1)).thenReturn("id");
        when(metaData.getColumnName(2)).thenReturn("name");

        when(resultSet.next()).thenReturn(true, true, false);
        when(resultSet.getString(1)).thenReturn("1", "22");
        when(resultSet.getString(2)).thenReturn("Alice", null);

        SqlQueryResult result = ExecutorRowMapper.fromResultSet(resultSet);

        assertNotNull(result);
        assertArrayEquals(new String[]{"id", "name"}, result.headers());
        assertEquals(2, result.rows().size());
        assertArrayEquals(new String[]{"1", "Alice"}, result.rows().get(0));
        assertArrayEquals(new String[]{"22", "NULL"}, result.rows().get(1));
        assertArrayEquals(new int[]{2, 5}, result.columnWidths());
    }

}