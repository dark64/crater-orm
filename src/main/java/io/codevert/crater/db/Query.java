package io.codevert.crater.db;

import io.codevert.crater.EntityMetadata;

import java.sql.*;
import java.util.Collections;
import java.util.List;

public class Query<T> {

    private QueryContext queryContext;
    private Class<T> entityClass;
    private EntityMetadata entityMetadata;

    private String sql;
    private String whereClause;
    private Object[] args;

    public Query(QueryContext queryContext, Class<T> entityClass) {
        this.queryContext = queryContext;
        this.entityClass = entityClass;
        this.entityMetadata = new EntityMetadata(entityClass);
    }

    public Query<T> sql(String sql, Object... args) {
        this.sql = sql;
        this.args = args;
        return this;
    }

    public Query<T> where(String where, Object... args) {
        this.whereClause = where;
        this.args = args;
        return this;
    }

    public Query<T> where(String where) {
        this.whereClause = where;
        return this;
    }

    public List<T> all() throws SQLException {
        PreparedStatement preparedStatement;
        Connection connection = queryContext.getConnection();

        if (sql != null) {
            preparedStatement = connection.prepareStatement(sql);
        } else {
            String statement = queryContext.getSqlBuilder().createSelectStatement(this);
            preparedStatement = connection.prepareStatement(statement);
        }

        if (args != null) {
            applyParameters(preparedStatement);
        }

        ResultSet resultSet = preparedStatement.executeQuery();
        ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
        int columnsCount = resultSetMetaData.getColumnCount();

        for (int i = 1; i <= columnsCount; i++) {
            System.out.print(resultSetMetaData.getColumnName(i));
            if (i != columnsCount) {
                System.out.print(", ");
            } else {
                System.out.println();
            }
        }

        //TODO: Implement result set mapping
        while (resultSet.next()) {
            for (int i = 1; i <= columnsCount; i++) {
                System.out.print(resultSet.getString(i));
                if (i != columnsCount) {
                    System.out.print(", ");
                }
            }
            System.out.println();
        }

        // return empty list for now
        return Collections.emptyList();
    }

    public T first() throws SQLException {
        List<T> result = all();
        return result.size() > 0 ? result.get(0) : null;
    }

    private void applyParameters(PreparedStatement state) throws SQLException {
        for (int i = 0; i < args.length; i++) {
            state.setObject(i + 1, args[i]);
        }
    }

    public Class<T> getEntityClass() {
        return entityClass;
    }

    public String getWhereClause() {
        return whereClause;
    }

    public EntityMetadata getEntityMetadata() {
        return entityMetadata;
    }
}
