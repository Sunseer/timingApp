package ru.agrin.timingApp.dao;

import ru.agrin.timingApp.model.Employee;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**Обработка SQL запросов по сотруднику.
 * Created by Grin on 20.02.2017.
 */
public class EmployeeDAOImpl implements EmployeeDAO {

    private final String SQL_FIND_ALL ="SELECT * FROM employees";
    private final String SQL_FIND_BY_NAME ="SELECT * FROM employees WHERE name like ?";
    private final String SQL_FIND_ALL_ABSENCE_BY_DATE = "SELECT * FROM employees e WHERE NOT EXISTS(SELECT 1 FROM history h where e.id=h.employee_id AND h.startJob >= ? AND DATE(h.startjob) <= ?)";
    private final String SQL_INSERT ="INSERT INTO employees (name, department) VALUES(?, ?)";
    private final String SQL_UPDATE ="UPDATE employees SET name=?, department=? WHERE id=?";
    private final String SQL_DELETE ="DELETE FROM employees WHERE id=?";

    private DataSource dataSource;

    public EmployeeDAOImpl() throws Exception {
        dataSource = new DataSource();
    }

    @Override
    public List<Employee> findAll()  {
        List<Employee> list = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet rs = null;

        try {
            connection = dataSource.getConnection();
            statement = connection.prepareStatement(SQL_FIND_ALL);
            rs = statement.executeQuery();

            while (rs.next()) {
                Employee tempEmployee = convertRowToEmployee(rs);
                list.add(tempEmployee);
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            dataSource.close(connection, statement, rs);
        }
        return list;
    }

    @Override
    public List<Employee>  findByName(String name) {
        List<Employee> list = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet rs = null;

        try {
            connection = dataSource.getConnection();
            statement = connection.prepareStatement(SQL_FIND_BY_NAME);
            statement.setString(1, name);
            rs = statement.executeQuery();

            while (rs.next()) {
                Employee tempEmployee = convertRowToEmployee(rs);
                list.add(tempEmployee);
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            dataSource.close(connection, statement, rs);
        }
        return list;
    }


    @Override
    public List<Employee> findAllAbsenceEmployeeByDate(Date startDate, Date endDate) {
        List<Employee> list = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet rs = null;

        try {
            connection = dataSource.getConnection();
            statement = connection.prepareStatement(SQL_FIND_ALL_ABSENCE_BY_DATE);
            statement.setDate(1, startDate);
            statement.setDate(2, endDate);
            rs = statement.executeQuery();

            while (rs.next()) {
                Employee tempEmployee = convertRowToEmployee(rs);
                list.add(tempEmployee);
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            dataSource.close(connection, statement, rs);
        }
        return list;
    }

    @Override
    public void insert(Employee employee) {
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = dataSource.getConnection();
            statement = connection.prepareStatement(SQL_INSERT);
            statement.setString(1, employee.getName());
            statement.setString(2, employee.getDepartment());
            statement.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            dataSource.close(connection, statement);
        }
    }

    @Override
    public void update(Employee employee) {
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = dataSource.getConnection();
            statement = connection.prepareStatement(SQL_UPDATE);
            statement.setString(1, employee.getName());
            statement.setString(2, employee.getDepartment());
            statement.setInt(3, employee.getId());
            statement.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            dataSource.close(connection, statement);
        }
    }

    @Override
    public void delete(int id) {
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = dataSource.getConnection();
            statement = connection.prepareStatement(SQL_DELETE);
            statement.setInt(1, id);
            statement.execute();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            dataSource.close(connection, statement);
        }
    }

    /**
     * Преобразование ряда SQL таблицы в сотрудника
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
