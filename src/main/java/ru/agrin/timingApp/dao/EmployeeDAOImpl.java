package ru.agrin.timingApp.dao;

import ru.agrin.timingApp.model.Employee;

import java.io.FileInputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**Обработка SQL запросов по сотруднику.
 * Created by Grin on 20.02.2017.
 */
public class EmployeeDAOImpl implements EmployeeDAO {
    private DataSource dataSource;

    public EmployeeDAOImpl() throws Exception {

        dataSource = new DataSource();
        Properties props = new Properties();
        props.load(new FileInputStream("demo.properties"));

        dataSource.setLogin(props.getProperty("user"));
        dataSource.setPassword(props.getProperty("password"));
        dataSource.setUrl(props.getProperty("dburl"));
    }

    @Override
    public List<Employee> findAll()  {
        List<Employee> list = new ArrayList<>();
        Connection connection = null;

        try {
            connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(SQL_FIND_ALL);
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                Employee tempEmployee = convertRowToEmployee(rs);
                list.add(tempEmployee);
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
    public List<Employee>  findByName(String name) {
        List<Employee> list = new ArrayList<>();
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(SQL_FIND_BY_NAME);
            statement.setString(1, name);
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                Employee tempEmployee = convertRowToEmployee(rs);
                list.add(tempEmployee);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dataSource.closeConnection(connection);
        }
        return list;
    }


    @Override
    public List<Employee> findAllAbsenceEmployeeByDate(Date startDate, Date endDate) {
        List<Employee> list = new ArrayList<>();
        Connection connection = null;

        try {
            connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(SQL_FIND_ALL_ABSENCE_BY_DATE);
            statement.setDate(1, startDate);
            statement.setDate(2, endDate);
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                Employee tempEmployee = convertRowToEmployee(rs);
                list.add(tempEmployee);
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
    public void insert(Employee employee) {
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(SQL_INSERT);
            statement.setString(1, employee.getName());
            statement.setString(2, employee.getDepartment());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dataSource.closeConnection(connection);
        }
    }

    @Override
    public void update(Employee employee) {
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(SQL_UPDATE);
            statement.setString(1, employee.getName());
            statement.setString(2, employee.getDepartment());
            statement.setInt(3, employee.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dataSource.closeConnection(connection);
        }
    }

    @Override
    public void delete(int id) {
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(SQL_DELETE);
            statement.setInt(1, id);
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dataSource.closeConnection(connection);
        }
    }

    /**
     * Преоразование ряда SQL таблицы в сотрудника
     * @param rs
     * @return сотрудника
     * @throws SQLException
     */
    protected Employee convertRowToEmployee(ResultSet rs) throws SQLException {
        int id = rs.getInt(1);
        String name = rs.getString(2);
        String department = rs.getString(3);
        Employee tempEmployee = new Employee(id, name, department);

        return tempEmployee;
    }
}
