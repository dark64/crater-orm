package io.codevert.crater.db;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import io.codevert.crater.builder.StandardSQLBuilder;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class DatabaseManager {

    private static final int MAX_POOL_SIZE = 10;
    private DataSource dataSource;

    private String driverClassName = System.getProperty("crater.driver-class-name");
    private String jdbcUrl = System.getProperty("crater.jdbc-url");
    private String username = System.getProperty("crater.username");
    private String password = System.getProperty("crater.password");
    private int maxPoolSize = Integer.parseInt(System.getProperty("crater.max-pool-size", String.valueOf(MAX_POOL_SIZE)));

    public DatabaseManager() { }

    protected Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    public void create() {
        if (dataSource != null) {
            return;
        }
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setMaximumPoolSize(maxPoolSize);
        hikariConfig.setDriverClassName(driverClassName);
        hikariConfig.setJdbcUrl(jdbcUrl);
        hikariConfig.setUsername(username);
        hikariConfig.setPassword(password);
        this.dataSource = new HikariDataSource(hikariConfig);
    }

    public <T> Query<T> get(Class<T> clazz) throws SQLException {
        QueryContext context = new QueryContext(getConnection(), new StandardSQLBuilder());
        return new Query<>(context, clazz);
    }

    public String getDriverClassName() {
        return driverClassName;
    }

    public void setDriverClassName(String driverClassName) {
        this.driverClassName = driverClassName;
    }

    public String getJdbcUrl() {
        return jdbcUrl;
    }

    public void setJdbcUrl(String jdbcUrl) {
        this.jdbcUrl = jdbcUrl;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getMaxPoolSize() {
        return maxPoolSize;
    }

    public void setMaxPoolSize(int maxPoolSize) {
        this.maxPoolSize = maxPoolSize;
    }
}
