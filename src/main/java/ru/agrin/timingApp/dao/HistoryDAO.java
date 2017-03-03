package ru.agrin.timingApp.dao;

import ru.agrin.timingApp.model.History;

import java.sql.Date;
import java.util.List;

/**
 * Основные методы для работы с историей посещений сотрудника.
 * Created by Grin on 20.02.2017.
 */
public interface HistoryDAO {
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
    public void startWork(History history);

    /**
     * Изменение посещения. Окончание работы.
     * @param history - посещение работы.
     */
    public void endWork(History history);
}
