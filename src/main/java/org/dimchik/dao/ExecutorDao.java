package org.dimchik.dao;

import org.dimchik.entity.SqlQueryResult;

import java.sql.SQLException;

public interface ExecutorDao {
    SqlQueryResult executeQuery(String sql) throws SQLException;
    int executeUpdate(String sql) throws SQLException;
}
