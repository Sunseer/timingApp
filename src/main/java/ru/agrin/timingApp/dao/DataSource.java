package ru.agrin.timingApp.dao;

/**
 * Модель подключения к базе данных.
 * Created by Grin on 19.02.2017.
 */
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public class DataSource {
    private String url;
    private String login;
    private String password;
    private Properties properties;

    public DataSource() {
        properties = new Properties();

        try {
            properties.load(new FileInputStream("demo.properties"));
        }
        catch (IOException exc){
            exc.printStackTrace();
        }

        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
        }
        catch (Exception exc) {
            exc.printStackTrace();
        }
        setLogin(properties.getProperty("user"));
        setPassword(properties.getProperty("password"));
        setUrl(properties.getProperty("dburl"));
    }

    private void setUrl(String url) {
        this.url = url;
    }

    private void setLogin(String login) {
        this.login = login;
    }

    private void setPassword(String password) {
        this.password = password;
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, login, password);
    }

    /**
     * Закрытие соединения с базой данных.
     * @param connection - соединение с СУБД.
     */
    public void close(Connection connection) {
        if (connection != null)
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Закрытие соединения и доступа к базе данных.
     * @param connection - соединение с СУБД.
     * @param statement - доступ к СУБД.
     */
    public void close(Connection connection, PreparedStatement statement){
        if (statement != null){
            try {
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        close(connection);
    }

    /**
     * Закрытие соединения, доступа к базе данных, и построчного доступа в таблице СУБД.
     * @param connection - соединение с СУБД.
     * @param statement - доступ к СУБД.
     * @param rs - построчный доступ в таблице СУБД.
     */
    public void close(Connection connection, PreparedStatement statement, ResultSet rs){
        if (rs != null){
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        close(connection, statement);
    }
}
