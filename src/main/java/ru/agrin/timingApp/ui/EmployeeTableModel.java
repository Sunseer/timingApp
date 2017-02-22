package ru.agrin.timingApp.ui;

import ru.agrin.timingApp.model.Employee;

import java.util.List;

import javax.swing.table.AbstractTableModel;

/** Таблица отчета.
 * Created by Grin on 21.02.2017.
 */
class EmployeeTableModel extends AbstractTableModel {
    public static final int OBJECT_COL = -1;
    private static final int ID_COL = 0;
    private static final int NAME_COL = 1;
    private static final int DEPARTMENT_COL = 2;

    private String[] columnNames = {"№ сотрудника", "ФИО", "Отдел"};
    private List<Employee> employees;

    public EmployeeTableModel(List<Employee> theEmployees) {
        employees = theEmployees;
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public int getRowCount() {
        return employees.size();
    }

    @Override
    public String getColumnName(int col) {
        return columnNames[col];
    }

    @Override
    public Object getValueAt(int row, int col) {

        Employee tempEmployee = employees.get(row);

        switch (col) {
            case ID_COL:
                return tempEmployee.getId();
            case NAME_COL:
                return tempEmployee.getName();
            case DEPARTMENT_COL:
                return tempEmployee.getDepartment();
            case OBJECT_COL:
                return tempEmployee;
            default:
                return tempEmployee.getName();
        }
    }

    @Override
    public Class getColumnClass(int c) {
        return getValueAt(0, c).getClass();
    }
}
