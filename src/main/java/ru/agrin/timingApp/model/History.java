package ru.agrin.timingApp.model;

import java.sql.Timestamp;

/** Модель истории посещений сотрудника.
 * Created by Grin on 18.02.2017.
 */
public class History {
    private int id;
    private int employeeId;
    private Timestamp startJob;
    private Timestamp endJob;
    private Employee employee;

    public History() {
    }

    public History(int id, int employee_id, Timestamp startJob, Timestamp endJob) {
        this.id = id;
        this.employeeId = employee_id;
        this.startJob = startJob;
        this.endJob = endJob;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employee_id) {
        this.employeeId = employee_id;
    }

    public Timestamp getStartJob() {
        return startJob;
    }

    public void setStartJob(Timestamp startJob) {
        this.startJob = startJob;
    }

    public Timestamp getEndJob() {
        return endJob;
    }

    public void setEndJob(Timestamp endJob) {
        this.endJob = endJob;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    @Override
    public String toString() {
        return String
                .format("Номер сотрудника=%s, Приход на работу=%s, Уход с работы=%s",
                        employeeId, startJob, endJob);
    }
}
