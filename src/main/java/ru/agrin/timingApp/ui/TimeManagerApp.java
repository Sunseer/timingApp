package ru.agrin.timingApp.ui;

import ru.agrin.timingApp.dao.EmployeeDAOImpl;
import ru.agrin.timingApp.dao.HistoryDAOImpl;
import ru.agrin.timingApp.model.Employee;
import ru.agrin.timingApp.model.History;

import java.awt.*;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;

import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JOptionPane;

import java.sql.Date;
import java.util.List;

/**
 * Основное окно программы
 * Created by Grin on 20.02.2017.
 */
public class TimeManagerApp extends JFrame {

    private JPanel contentPane;
    private JTextField idTextField;
    private JButton btnStart;
    private JButton btnStop;
    private JTextField startDate;
    private JButton btnPresence;
    private JButton btnAbsence;
    private JTextField endDate;
    private JButton btnHistoryEmp;
    private JTextField nameTextField;
    private JButton btnSearch;
    private JScrollPane scrollPane;
    private JTable table;

    private EmployeeDAOImpl employeeDAOImpl;
    private HistoryDAOImpl historyDAOImpl;
    private JPanel PanelBtn;
    private JButton btnAddEmployee;
    private JButton btnUpdateEmployee;
    private JButton btnDeleteEmployee;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    TimeManagerApp frame = new TimeManagerApp();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public TimeManagerApp() {

        try {
            employeeDAOImpl = new EmployeeDAOImpl();
            historyDAOImpl = new HistoryDAOImpl();
        } catch (Exception exc) {
            JOptionPane.showMessageDialog(this, "Ошибка: " + exc, "Ошибка",
                    JOptionPane.ERROR_MESSAGE);
        }

        setTitle("Программа учета рабочего времени");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(300, 300, 580, 600);
        setResizable(false);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(new BorderLayout(0, 0));
        setContentPane(contentPane);

        JPanel panel = new JPanel();
        FlowLayout flowLayout = (FlowLayout) panel.getLayout();
        flowLayout.setAlignment(FlowLayout.LEFT);

        panel.setPreferredSize(new Dimension(580,130));
        panel.setMinimumSize(new Dimension(580,130));
        panel.setMaximumSize(new Dimension(580,130));

        contentPane.add(panel, BorderLayout.NORTH);

        JLabel lblEnterId = new JLabel("Введите № сотрудника:");
        lblEnterId.setPreferredSize(new Dimension(160, 13));
        panel.add(lblEnterId);

        idTextField = new JTextField();
        panel.add(idTextField);
        idTextField.setColumns(10);

        btnStart = new JButton("Начало работы");
        btnStart.setPreferredSize(new Dimension(125, 25));
        btnStart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                try {
                    int employeeId = Integer.valueOf(idTextField.getText());
                    History history = new History();
                    history.setEmployeeId(employeeId);
                    historyDAOImpl.startWork(history);

                    JOptionPane.showMessageDialog(TimeManagerApp.this, "Данные добавлены успешно",
                            "Сообщение", JOptionPane.INFORMATION_MESSAGE);
                }
                catch (Exception exc) {
                    JOptionPane.showMessageDialog(TimeManagerApp.this, "Ошибка: такого № " +
                            "сотрудника не существует, введите корректный № " + exc, "Ошибка", JOptionPane.ERROR_MESSAGE);
                }

            }
        });
        panel.add(btnStart);

        btnStop = new JButton("Конец работы");
        btnStop.setPreferredSize(new Dimension(125, 25));
        btnStop.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                try {
                    int employeeId = Integer.valueOf(idTextField.getText());
                    History history = new History();
                    history.setEmployeeId(employeeId);
                    historyDAOImpl.endWork(history);

                    JOptionPane.showMessageDialog(TimeManagerApp.this, "Данные добавлены успешно",
                            "Сообщение", JOptionPane.INFORMATION_MESSAGE);
                }
                catch (Exception exc) {
                    JOptionPane.showMessageDialog(TimeManagerApp.this, "Ошибка: такого № " +
                            "сотрудника не существует, введите корректный №" + exc, "Ошибка", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        panel.add(btnStop);

        JLabel lblStartDate = new JLabel("Введите начало периода:");
        lblStartDate.setPreferredSize(new Dimension(160, 13));
        panel.add(lblStartDate);

        startDate = new JTextField();
        panel.add(startDate);
        startDate.setColumns(10);

        btnPresence = new JButton("Присутствие");
        btnPresence.setPreferredSize(new Dimension(125, 25));
        btnPresence.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                EmployeeHistoryTableModel employeeHistoryTableModel = null;

                try {
                    Date start = Date.valueOf(startDate.getText());
                    Date end = Date.valueOf(endDate.getText());

                    List<History> histories = null;

                    histories = historyDAOImpl.findAllPresenceEmployeeByDate(start, end);

                    employeeHistoryTableModel = new EmployeeHistoryTableModel(histories);
                    new EmployeeHistoryFrame(employeeHistoryTableModel);

                } catch (Exception exc) {
                    JOptionPane.showMessageDialog(TimeManagerApp.this, "Ошибка: Введите " +
                                    "начальную и конечную дату в формате YYYY-MM-DD " + exc,
                            "Ошибка", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        panel.add(btnPresence);

        btnAbsence = new JButton("Отсутствие");
        btnAbsence.setPreferredSize(new Dimension(125, 25));
        btnAbsence.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Date start = Date.valueOf(startDate.getText());
                    Date end = Date.valueOf(endDate.getText());

                    List<Employee> employees = null;

                    employees = employeeDAOImpl.findAllAbsenceEmployeeByDate(start, end);

                    EmployeeTableModel model = new EmployeeTableModel(employees);
                    table.setModel(model);

                } catch (Exception exc) {
                    JOptionPane.showMessageDialog(TimeManagerApp.this, "Ошибка: Введите " +
                                    "начальную и конечную дату в формате YYYY-MM-DD " + exc,
                            "Ошибка", JOptionPane.ERROR_MESSAGE);
                }

            }
        });
        panel.add(btnAbsence);

        JLabel lblEndDate = new JLabel("Введите конец периода:");
        lblEndDate.setPreferredSize(new Dimension(160, 13));
        panel.add(lblEndDate);

        endDate = new JTextField();
        panel.add(endDate);
        endDate.setColumns(10);

        btnHistoryEmp = new JButton("История сотрудника");
        btnHistoryEmp.setPreferredSize(new Dimension(255, 25));
        btnHistoryEmp.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                EmployeeHistoryTableModel employeeHistoryTableModel = null;

                try {
                    int employeeId = Integer.valueOf(idTextField.getText());
                    Date start = Date.valueOf(startDate.getText());
                    Date end = Date.valueOf(endDate.getText());

                    List<History> histories = null;

                    histories = historyDAOImpl.findByEmployeeIdAndDate(employeeId, start, end);

                    employeeHistoryTableModel = new EmployeeHistoryTableModel(histories);
                    new EmployeeHistoryFrame(employeeHistoryTableModel);

                } catch (Exception exc) {
                    JOptionPane.showMessageDialog(TimeManagerApp.this, "Ошибка: Введите " +
                                    "начальную и конечную дату в формате YYYY-MM-DD. Укажите правильный № сотрудника " + exc,
                            "Ошибка", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        panel.add(btnHistoryEmp);

        JLabel lblEnterName = new JLabel("Введите ФИО:");
        lblEnterName.setPreferredSize(new Dimension(160, 13));
        panel.add(lblEnterName);

        nameTextField = new JTextField();
        panel.add(nameTextField);
        nameTextField.setColumns(10);

        btnSearch = new JButton("Поиск сотрудника");
        btnSearch.setPreferredSize(new Dimension(255, 25));
        btnSearch.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                try {
                    String lastName = nameTextField.getText();
                    List<Employee> employees = null;

                    if (lastName != null && lastName.trim().length() > 0) {
                        employees = employeeDAOImpl.findByName(lastName);
                    } else {
                        employees = employeeDAOImpl.findAll();
                    }
                    EmployeeTableModel model = new EmployeeTableModel(employees);
                    table.setModel(model);
                } catch (Exception exc) {
                    JOptionPane.showMessageDialog(TimeManagerApp.this, "Ошибка: " + exc,
                            "Ошибка", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        panel.add(btnSearch);

        scrollPane = new JScrollPane();
        contentPane.add(scrollPane, BorderLayout.CENTER);

        table = new JTable();
        scrollPane.setViewportView(table);

        PanelBtn = new JPanel();
        contentPane.add(PanelBtn, BorderLayout.SOUTH);

        btnAddEmployee = new JButton("Добавить сотрудника");
        btnAddEmployee.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                EmployeeDialog dialog = new EmployeeDialog(TimeManagerApp.this, employeeDAOImpl);

                dialog.setVisible(true);
            }
        });
        PanelBtn.add(btnAddEmployee);

        btnUpdateEmployee = new JButton("Редактировать сотрудника");
        btnUpdateEmployee.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int row = table.getSelectedRow();

                if (row < 0) {
                    JOptionPane.showMessageDialog(TimeManagerApp.this, "Вы должны выбрать " +
                                    "сотрудника", "Ошибка", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                Employee tempEmployee = (Employee) table.getValueAt(row, EmployeeTableModel.OBJECT_COL);

                EmployeeDialog dialog = new EmployeeDialog(TimeManagerApp.this, employeeDAOImpl,
                        tempEmployee, true);

                dialog.setVisible(true);

            }
        });
        PanelBtn.add(btnUpdateEmployee);

        btnDeleteEmployee = new JButton("Удалить сотрудника");
        btnDeleteEmployee.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                try {
                    int row = table.getSelectedRow();

                    if (row < 0) {
                        JOptionPane.showMessageDialog(TimeManagerApp.this,
                                "Вы должны выбрать сотрудника", "Ошибка", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    int response = JOptionPane.showConfirmDialog(
                            TimeManagerApp.this, "Удалить сотрудника?", "Подтверждение",
                            JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

                    if (response != JOptionPane.YES_OPTION) {
                        return;
                    }

                    Employee tempEmployee = (Employee) table.getValueAt(row, EmployeeTableModel.OBJECT_COL);

                    employeeDAOImpl.delete(tempEmployee.getId());

                    refreshEmployeesView();

                    JOptionPane.showMessageDialog(TimeManagerApp.this,
                            "Сотрудник удален", "Удаление сотрадника",
                            JOptionPane.INFORMATION_MESSAGE);

                } catch (Exception exc) {
                    JOptionPane.showMessageDialog(TimeManagerApp.this,
                            "Ошибка удаления: " + exc, "Ошибка",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        PanelBtn.add(btnDeleteEmployee);
    }

    public void refreshEmployeesView() {

        try {
            List<Employee> employees = employeeDAOImpl.findAll();

            EmployeeTableModel model = new EmployeeTableModel(employees);

            table.setModel(model);
        } catch (Exception exc) {
            JOptionPane.showMessageDialog(this, "Ошибка: " + exc, "Ошибка",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}
