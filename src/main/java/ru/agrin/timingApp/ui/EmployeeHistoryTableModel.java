package ru.agrin.timingApp.ui;

import ru.agrin.timingApp.model.History;

import javax.swing.table.AbstractTableModel;
import java.util.List;

/** Таблица отчета.
 * Created by Grin on 21.02.2017.
 */
public class EmployeeHistoryTableModel extends AbstractTableModel{
    private static final int OBJECT_COL = -1;
    private static final int NAME_COL = 0;
    private static final int STARTJOB_COL = 1;
    private static final int ENDJOB_COL = 2;

    private String[] columnNames = {"ФИО", "Время прихода", "Время ухода"};
    private List<History> histories;


    public EmployeeHistoryTableModel(List<History> theHistories) {
        histories = theHistories;
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public int getRowCount() {
        return histories.size();
    }

    @Override
    public String getColumnName(int col) {
        return columnNames[col];
    }
    @Override
    public Object getValueAt(int row, int col) {

        History tempHistory = histories.get(row);

        switch (col) {
            case NAME_COL:
                return tempHistory.getEmployee().getName();
            case STARTJOB_COL:
                return tempHistory.getStartJob();
            case ENDJOB_COL:
                return tempHistory.getEndJob();
            case OBJECT_COL:
                return tempHistory;
            default:
                return tempHistory.getEmployee().getName();
        }
    }
    @Override
    public Class getColumnClass(int c) {
        return getValueAt(0, c).getClass();
    }

}

