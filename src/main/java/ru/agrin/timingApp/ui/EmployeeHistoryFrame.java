package ru.agrin.timingApp.ui;

import ru.agrin.timingApp.dao.HistoryDAOImpl;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/** Окно отчета
 * Created by Grin on 21.02.2017.
 */
public class EmployeeHistoryFrame extends JFrame {
    private JFrame frame;
    private JTable employeeHistoryTable;
    private JScrollPane scrollPane;
    private HistoryDAOImpl historyDAOImpl;
    private JPanel contentPane;
    private EmployeeHistoryTableModel employeeHistoryTableModel;
    private DateCellRenderer dateCellRenderer;

    public EmployeeHistoryFrame(EmployeeHistoryTableModel employeeHistoryTableModel){
        dateCellRenderer = new DateCellRenderer();
        this.employeeHistoryTableModel = employeeHistoryTableModel;

        try {
            historyDAOImpl = new HistoryDAOImpl();
        } catch (Exception exc) {
            JOptionPane.showMessageDialog(this, "Ошибка: " + exc, "Ошибка",
                    JOptionPane.ERROR_MESSAGE);
        }
        setTitle("Программа учета рабочего времени");

        setBounds(100, 100, 300, 400);

        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(new BorderLayout(0, 0));
        setContentPane(contentPane);

        scrollPane = new JScrollPane();
        contentPane.add(scrollPane, BorderLayout.CENTER);

        employeeHistoryTable = new JTable(employeeHistoryTableModel);
        employeeHistoryTable.getColumnModel().getColumn(1).setCellRenderer(dateCellRenderer);
        employeeHistoryTable.getColumnModel().getColumn(2).setCellRenderer(dateCellRenderer);
        scrollPane.setViewportView(employeeHistoryTable);

        setVisible(true);
        pack();
    }
}
