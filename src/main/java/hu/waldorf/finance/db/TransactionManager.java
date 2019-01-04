package hu.waldorf.finance.db;

import com.google.inject.Inject;

import java.sql.Connection;
import java.sql.SQLException;

public class TransactionManager {
    private final Connection connection;

    @Inject
    public TransactionManager(Connection connection) {
        this.connection = connection;
    }

    public void commit() {
        if (connection == null) {
            throw new RuntimeException("no connection.");
        }

        try {
            connection.commit();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void rollback() {
        if (connection == null) {
            throw new RuntimeException("no connection.");
        }

        try {
            connection.rollback();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
