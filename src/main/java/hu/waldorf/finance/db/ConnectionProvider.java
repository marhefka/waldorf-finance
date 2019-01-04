package hu.waldorf.finance.db;

import com.google.inject.Provider;
import com.google.inject.Singleton;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Singleton
public class ConnectionProvider implements Provider<Connection> {
    private final static int MAX_CONNECTIONS = 5;

    private final String userName = "root";
    private final String password = "";
    private final String url = "jdbc:mysql://localhost:3306/waldorf";

    private ThreadLocal<Connection> connectionThreadLocal = new ThreadLocal<>();
    private static ConnectionProvider connectionProvider;
    private int numberOfOpenConnections;

    // WORKAROUND to access ConnectionProvider from methodinterceptor TransactionSupporter
    public static ConnectionProvider getInstance() {
        return connectionProvider;
    }

    public ConnectionProvider() {
        if (connectionProvider != null) {
            throw new RuntimeException("connectionprovider must be null.");
        }

        connectionProvider = this;
    }

    @Override
    public Connection get() {
        if (connectionThreadLocal.get() != null) {
            return connectionThreadLocal.get();
        }

        if (numberOfOpenConnections >= MAX_CONNECTIONS) {
            throw new RuntimeException("Too many open connections.");
        }

        try {
            Connection connection = DriverManager.getConnection(url, userName, password);
            connection.setAutoCommit(false);
            connectionThreadLocal.set(connection);
            ++numberOfOpenConnections;
            return connection;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void close() {
        if (connectionThreadLocal.get() != null) {
            try {
                connectionThreadLocal.get().close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } finally {
                connectionThreadLocal.remove();
            }
        }
    }
}
