package ru.agrin.timingApp.dao;

import ru.agrin.timingApp.model.History;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

/**
 * Основные методы для работы с историей посещений сотрудника.
 * Created by Grin on 20.02.2017.
 */
public interface HistoryDAO {
    public static final String SQL_FIND_ALL_PRESENCE_BY_DATE = "SELECT * from employees e INNER JOIN history h ON e.id = h.employee_id WHERE h.startJob >= ? AND h.startJob < ?";
    public static final String SQL_FIND_BY_EMPLOYEE_ID_AND_DATE ="SELECT * from employees e INNER JOIN history h ON e.id = h.employee_id WHERE employee_id = ? AND startJob >= ? AND startJob < ?";
    public static final String SQL_INSERT ="INSERT INTO history (employee_id, startJob) VALUES(?, ?)";
    public static final String SQL_UPDATE ="UPDATE history SET endJob=? WHERE employee_id=? AND endJob IS NULL";

    /**
     * Список всех присутствующих на работе сотрудников за заданный период.
     * @param startDate - начальная дата.
     * @param endDate - конечная дата.
     * @return список истории сотрудника.
     */
    public List<History> findAllPresenceEmployeeByDate(Date startDate, Date endDate);

    /**
     * Список посещений сотрудника за определенный период.
     * @param id - номер сотрудника.
     * @param startDate - начальная дата.
     * @param endDate - конечная дата.
     * @return список посещений сотрудника.
     */
    public List<History> findByEmployeeIdAndDate(int id, Date startDate, Date endDate);

    /**
     * Добавление посещения. Начало работы.
     * @param history - посещение работы.
     */
    public void insert(History history);

    /**
     * Изменение посещения. Окончание работы.
     * @param history - посещение работы.
     */
    public void update(History history);
}
