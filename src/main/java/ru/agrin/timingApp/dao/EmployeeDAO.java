package ru.agrin.timingApp.dao;

import ru.agrin.timingApp.model.Employee;

import java.sql.Date;
import java.util.List;

/**
 * Основные методы для работы с сотрудником.
 * Created by Grin on 20.02.2017.
 */
public interface EmployeeDAO {
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
