package io.codevert.crater.db;

import io.codevert.crater.builder.SQLBuilder;

import java.sql.Connection;

public class QueryContext {

    private Connection connection;
    private SQLBuilder sqlBuilder;

    protected QueryContext() { }

    protected QueryContext(Connection connection, SQLBuilder sqlBuilder) {
        this.connection = connection;
        this.sqlBuilder = sqlBuilder;
    }

    protected Connection getConnection() {
        return connection;
    }

    protected void setConnection(Connection connection) {
        this.connection = connection;
    }

    protected SQLBuilder getSqlBuilder() {
        return sqlBuilder;
    }

    protected void setSqlBuilder(SQLBuilder sqlBuilder) {
        this.sqlBuilder = sqlBuilder;
    }
}
