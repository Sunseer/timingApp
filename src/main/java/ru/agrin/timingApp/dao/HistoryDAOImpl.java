package ru.agrin.timingApp.dao;

import ru.agrin.timingApp.model.Employee;
import ru.agrin.timingApp.model.History;

import java.io.FileInputStream;
import java.sql.*;
import java.sql.Date;
import java.util.*;

/** Обработка SQL запросов по истории сотрудника.
 * Created by Grin on 20.02.2017.
 */
public class HistoryDAOImpl implements HistoryDAO{
    private DataSource dataSource;
    private EmployeeDAOImpl employeeDAOImpl;

    public HistoryDAOImpl() throws Exception {

        dataSource = new DataSource();
        Properties props = new Properties();
        props.load(new FileInputStream("demo.properties"));

        dataSource.setLogin(props.getProperty("user"));
        dataSource.setPassword(props.getProperty("password"));
        dataSource.setUrl(props.getProperty("dburl"));

        employeeDAOImpl = new EmployeeDAOImpl();
    }

    @Override
    public List<History> findAllPresenceEmployeeByDate(Date startDate, Date endDate) {
        List<History> list = new ArrayList<>();
        Connection connection = null;

        try {
            connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(SQL_FIND_ALL_PRESENCE_BY_DATE);
            statement.setDate(1, startDate);
            statement.setDate(2, endDate);
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                Employee tempEmployee = employeeDAOImpl.convertRowToEmployee(rs);
                History tempHistory = convertRowToHistory(rs);
                tempHistory.setEmployee(tempEmployee);
                list.add(tempHistory);
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            dataSource.closeConnection(connection);
        }
        return list;
    }

    @Override
    public List<History> findByEmployeeIdAndDate(int employeeId, Date startDate, Date endDate) {
        List<History> list = new ArrayList<>();
        Connection connection = null;

        try {
            connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(SQL_FIND_BY_EMPLOYEE_ID_AND_DATE);
            statement.setInt(1, employeeId);
            statement.setDate(2, startDate);
            statement.setDate(3, endDate);
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                Employee tempEmployee = employeeDAOImpl.convertRowToEmployee(rs);
                History tempHistory = convertRowToHistory(rs);
                tempHistory.setEmployee(tempEmployee);
                list.add(tempHistory);
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            dataSource.closeConnection(connection);
        }
        return list;
    }

    @Override
    public void insert(History history) {
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(SQL_INSERT);
            statement.setInt(1, history.getEmployeeId());
            statement.setTimestamp(2, new Timestamp(System.currentTimeMillis()));
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dataSource.closeConnection(connection);
        }
    }

    @Override
    public void update(History history) {
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(SQL_UPDATE);
            statement.setTimestamp(1, new Timestamp(System.currentTimeMillis()));
            statement.setInt(2, history.getEmployeeId());
            //statement.setString(3,"IS NULL");
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dataSource.closeConnection(connection);
        }
    }

    /**
     *  Преоразование ряда SQL таблицы в историю посещений сотрудника
     * @param rs
     * @return посещение сотрудника
     * @throws SQLException
     */
    private History convertRowToHistory(ResultSet rs) throws SQLException {
        int id = rs.getInt(4);
        int employee_id = rs.getInt(5);
        Timestamp startDateTime = rs.getTimestamp(6);
        Timestamp endDateTime = rs.getTimestamp(7);

        History tempTimeManager = new History(id, employee_id, startDateTime, endDateTime);

        return tempTimeManager;
    }

}
