package io.codevert.crater;

import io.codevert.crater.db.DatabaseManager;

import java.sql.SQLException;
import java.util.List;

public class Main {

    public static void main(String[] args) throws SQLException {
        DatabaseManager databaseManager = new DatabaseManager();
        databaseManager.setDriverClassName("org.h2.Driver");
        databaseManager.setJdbcUrl("jdbc:h2:tcp://localhost/~/test");
        databaseManager.setUsername("sa");
        databaseManager.create();

        // query
        databaseManager.get(Student.class).all();
    }
}