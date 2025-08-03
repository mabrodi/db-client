package org.dimchik.dao.jdbc;

import org.dimchik.dao.ExecutorDao;
import org.dimchik.dao.mapper.ExecutorRowMapper;
import org.dimchik.entity.SqlQueryResult;

import javax.sql.DataSource;
import java.sql.*;

public class ExecutorDaoJdbc implements ExecutorDao {
    private final DataSource dataSource;

    public ExecutorDaoJdbc(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public SqlQueryResult executeQuery(String sql) throws SQLException {
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            return ExecutorRowMapper.fromResultSet(resultSet);
        }
    }

    @Override
    public int executeUpdate(String sql) throws SQLException {
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {
            return statement.executeUpdate(sql);
        }
    }
}
