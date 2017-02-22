package ru.agrin.timingApp.model;

import java.util.List;

/** Модель сотрудника.
 * Created by Grin on 18.02.2017.
 */
public class Employee {
    private int id;
    private String name;
    private String department;
    private List<History> history;

    public Employee(String name, String department) {

        this(0, name, department);
    }

    public Employee(int id, String name, String department) {
        super();
        this.id = id;
        this.name = name;
        this.department = department;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public List<History> getHistory() {
        return history;
    }

    public void setHistory(List<History> history) {
        this.history = history;
    }

    @Override
    public String toString() {
        return String
                .format("Employee [id=%s, name=%s, department=%s, time:%s]",
                        id, name, department, history);
    }
}
