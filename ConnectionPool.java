package com.oxagile.eshop.dao.pool;

import com.oxagile.eshop.exceptions.ConnectionPoolException;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class ConnectionPool {
    private static final Logger LOGGER = Logger.getLogger(ConnectionPool.class);

    /**
     * Singleton instance
     */
    private static volatile ConnectionPool instance;

    /**
     * Configuration constants for the need to create a pool
     */
    private static final String DB_PROPERTY_FILE = "dbconfig";
    private static final String DB_DRIVER_NAME = "db.driver.name";
    private static final String DB_URL = "db.url";
    private static final String DB_LOGIN = "db.login";
    private static final String DB_PASS = "db.pass";
    private static final int MAX_CONNECTION_COUNT = 10;
    private static final int MIN_CONNECTION_COUNT = 5;

    private static String url;
    private static String login;
    private static String pass;
    private static String driverName;

    static {
        ResourceBundle rb = ResourceBundle.getBundle(DB_PROPERTY_FILE);
        url = rb.getString(DB_URL);
        login = rb.getString(DB_LOGIN);
        pass = rb.getString(DB_PASS);
        driverName = rb.getString(DB_DRIVER_NAME);
    }

    /**
     * Variable leading accounting of current connections
     */
    private volatile int currentConnectionNumber = MIN_CONNECTION_COUNT;
    private BlockingQueue<Connection> pool = new ArrayBlockingQueue<Connection>(MAX_CONNECTION_COUNT, true);

    /**
     * Singleton of connection pool
     *
     * @return instance
     */
    public static ConnectionPool getInstance() {
        if (instance == null) {
            synchronized (ConnectionPool.class) {
                if (instance == null) {
                    instance = new ConnectionPool();
                }
            }
        }
        return instance;
    }

    /**
     * The constructor creates an instance of the pool.
     * Initializes a constant number of connections = 5.
     */
    private ConnectionPool() {
        try {
            Class.forName(driverName);
        } catch (ClassNotFoundException e) {
            LOGGER.error(e.getMessage(), e);
        }
        for (int i = 0; i < MIN_CONNECTION_COUNT; i++) {
            try {
                pool.add(DriverManager.getConnection(url, login, pass));
            } catch (SQLException e) {
                LOGGER.error(e.getMessage(), e);
            }
        }
    }

    /**
     * The method provides the user with a copy of connection from pool
     *
     * @return Connection
     * //     * @throws ConnectionPoolException
     */
    public Connection getConnection() throws ConnectionPoolException {
        Connection connection;
        try {
            if (pool.isEmpty() && currentConnectionNumber < MAX_CONNECTION_COUNT) {
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
     * @param connection
     * @throws ConnectionPoolException
     */
    public void closeConnection(Connection connection) throws ConnectionPoolException {
        if (connection != null) {
            if (currentConnectionNumber > MIN_CONNECTION_COUNT) {
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
            Class.forName(driverName);
        } catch (ClassNotFoundException e) {
            throw new ConnectionPoolException("DB driver wasn't found", e);
        }
        try {
            pool.add(DriverManager.getConnection(url, login, pass));
            currentConnectionNumber++;
        } catch (SQLException e) {
            throw new ConnectionPoolException("New connection wasn't added in the connection pool", e);
        }
    }
}
