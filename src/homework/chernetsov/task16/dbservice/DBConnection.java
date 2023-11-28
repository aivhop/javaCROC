package homework.chernetsov.task16.dbservice;

import homework.chernetsov.task15.exceptions.ConnectionException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection implements AutoCloseable {
    public static final String DEFAULT_URL = "jdbc:h2:mem:~/database";
    public static final String DEFAULT_USER = "user";
    public static final String DEFAULT_PASSWORD = "";
    private final String url;
    private final String user;
    private final String password;

    private final Connection connection;


    private DBConnection(String url, String user, String password, Connection connection) {
        this.url = url;
        this.user = user;
        this.password = password;
        this.connection = connection;
    }

    public static DBConnection tryConnect(String url, String user, String password) throws ConnectionException {
        try {
            Connection connection = DriverManager.getConnection(url, user, password);
            return new DBConnection(url, user, password, connection);
        } catch (SQLException e) {
            throw new ConnectionException(e);
        }
    }

    public static DBConnection tryConnect(String url) throws ConnectionException {
        return tryConnect(url, DEFAULT_USER, DEFAULT_PASSWORD);
    }

    public static DBConnection tryConnect() throws ConnectionException {
        return tryConnect(DEFAULT_URL, DEFAULT_USER, DEFAULT_PASSWORD);
    }

    public String getUrl() {
        return url;
    }

    public String getUser() {
        return user;
    }

    public Connection getConnection() {
        return connection;
    }

    @Override
    public void close() throws Exception {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }
}
