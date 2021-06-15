package com.oxagile.eshop.dao;

import com.oxagile.eshop.exceptions.ConnectionPoolException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class ConnectionPool {
    private static final Logger log = LogManager.getLogger(ConnectionPool.class);
    /**
     * Configuration constants for the need to create a pool
     */
    private static final String DB_PROPERTY_FILE = "dbconfig";
    private static final String DB_DRIVER_NAME = "db.driver.name";
    private static final String DB_URL = "db.url";
    private static final String DB_LOGIN = "db.login";
    private static final String DB_PASSWORD = "db.password";
    private static final String DB_MAX_CONNECTION_COUNT = "db.maxConnection";
    private static final String DB_MIN_CONNECTION_COUNT = "db.minConnection";

    private static String url;
    private static String login;
    private static String password;
    private static String driverName;
    private static int minConnectionCount;
    private static int maxConnectionCount;

    static {
        ResourceBundle rb = ResourceBundle.getBundle(DB_PROPERTY_FILE);
        url = rb.getString(DB_URL);
        login = rb.getString(DB_LOGIN);
        password = rb.getString(DB_PASSWORD);
        driverName = rb.getString(DB_DRIVER_NAME);
        minConnectionCount = Integer.parseInt(rb.getString(DB_MIN_CONNECTION_COUNT));
        maxConnectionCount = Integer.parseInt(rb.getString(DB_MAX_CONNECTION_COUNT));

        try{
            Class.forName(driverName);
        } catch (ClassNotFoundException e) {
            log.error(e.getMessage(), e);
        }
    }

    /**
     * Variable leading accounting of current connections
     */
    private volatile int currentConnectionNumber = minConnectionCount;
    private final BlockingQueue<Connection> pool = new ArrayBlockingQueue<>(maxConnectionCount, true);

    /**
     * ConnectionPoolHolder is loaded on the first execution of ConnectionPool.getInstance()
     * or the first access to ConnectionPoolHolder.INSTANCE, not before.
     */
    private static class ConnectionPoolHolder {
        private static final ConnectionPool INSTANCE = new ConnectionPool();
    }

    /**
     * Singleton of connection pool
     *
     * @return instance
     */
    public static ConnectionPool getInstance() {
        return ConnectionPoolHolder.INSTANCE;
    }

    /**
     * The constructor creates an instance of the pool.
     * Initializes a constant number of connections
     */
    private ConnectionPool() {
        for (int i = 0; i < minConnectionCount; i++) {
            try {
                pool.add(DriverManager.getConnection(url, login, password));
            } catch (SQLException e) {
                log.error(e.getMessage(), e);
            }
        }
    }

    /**
     * The method provides the user with a copy of connection from pool
     *
     * @return Connection
     * // * @throws ConnectionPoolException
     */
    public Connection getConnection() throws ConnectionPoolException {
        Connection connection;
        try {
            if (pool.isEmpty() && currentConnectionNumber < maxConnectionCount) {
                openAdditionalConnection();
            }
            connection = pool.take();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new ConnectionPoolException("Max count of connections was reached", e);
        }
        return connection;
    }

    /**
     * The method return the connection back to the pool
     * when you are finished work with him.
     *
     * @param connection connection
     * @throws ConnectionPoolException
     */
    public void closeConnection(Connection connection) throws ConnectionPoolException {
        if (connection != null) {
            if (currentConnectionNumber > minConnectionCount) {
                currentConnectionNumber--;
            }
            try {
                pool.put(connection);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new ConnectionPoolException("Connection wasn't returned to the pool properly", e);
            }
        }
    }

    /**
     * Method providing additional
     * connection to the database if necessary
     *
     * @throws ConnectionPoolException
     */
    private void openAdditionalConnection() throws ConnectionPoolException {
        try {
            pool.add(DriverManager.getConnection(url, login,password));
            currentConnectionNumber++;
        } catch (SQLException e) {
            throw new ConnectionPoolException("New connection wasn't added in the connection pool", e);
        }
    }
}
