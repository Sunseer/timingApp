package ru.agrin.timingApp.dao;

import ru.agrin.timingApp.model.Employee;

import java.sql.Date;
import java.util.List;

/**
 * Основные методы для работы с сотрудником.
 * Created by Grin on 20.02.2017.
 */
public interface EmployeeDAO {
    public static final String SQL_FIND_ALL ="SELECT * FROM employees";
    public static final String SQL_FIND_BY_NAME ="SELECT * FROM employees WHERE name like ?";
    public static final String SQL_FIND_ALL_ABSENCE_BY_DATE = "SELECT * FROM employees e WHERE NOT EXISTS(SELECT 1 FROM history h where e.id=h.employee_id AND h.startJob >= ? AND h.startjob < ?)";
    public static final String SQL_INSERT ="INSERT INTO employees (name, department) VALUES(?, ?)";
    public static final String SQL_UPDATE ="UPDATE employees SET name=?, department=? WHERE id=?";
    public static final String SQL_DELETE ="DELETE FROM employees WHERE id=?";

    /**
     * Поиск всех сотрудников.
     * @return список сотрудников.
     */
    public List<Employee> findAll();

    /**
     * Поиск сотрудника.
     * @param name - им.
     * @return список.
     */
    public List<Employee> findByName(String name);

    /**
     * Поиск сотрудника по номеру.
     * @param id - номер.
     * @return список.
     */
   // public Employee findById(int id);

    /**
     * Список отсутствующих сотрудников за заданный период.
     * @param startDate - начальная дата.
     * @param endDate - конечная дата.
     * @return  список посещений сотрудника
     */
    public List<Employee> findAllAbsenceEmployeeByDate(Date startDate, Date endDate);

    /**
     * Добавление сотрудника.
     * @param employee - сотрудник.
     */
    public void insert(Employee employee);

    /**
     * Обновление сотрудника.
     * @param employee - сотрудник.
     */
    public void update(Employee employee);

    /**
     * Удаление сотрудника
     * @param id - номер сотрудника.
     */
    public void delete(int id);
}
