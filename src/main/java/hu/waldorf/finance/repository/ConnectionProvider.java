package hu.waldorf.finance.repository;

import com.google.inject.Provider;
import com.google.inject.Singleton;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Singleton
public class ConnectionProvider implements Provider<Connection> {
    private final String userName = "root";
    private final String password = "";
    private final String url = "jdbc:mysql://localhost:3306/waldorf";

    private Connection conn;

    @Override
    public Connection get() {
        if (conn != null) {
            return conn;
        }

        try {
            conn = DriverManager.getConnection(url, userName, password);
            conn.setAutoCommit(false);
            return conn;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void close() {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } finally {
                conn = null;
            }
        }
    }

    public void commitTransacion() {
        if (conn == null) {
            throw new RuntimeException("no connection.");
        }

        try {
            get().commit();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void rollbackTransacion() {
        if (conn == null) {
            throw new RuntimeException("no connection.");
        }

        try {
            get().rollback();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
