package ru.agrin.timingApp.ui;

import ru.agrin.timingApp.dao.EmployeeDAOImpl;
import ru.agrin.timingApp.model.Employee;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import javax.swing.JLabel;
import javax.swing.JTextField;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/** Диалоговое окно при добавление и изменение сотрудника.
 * Created by Grin on 21.02.2017.
 */

public class EmployeeDialog extends JDialog {

    private final JPanel contentPanel = new JPanel();
    private JTextField nameTextField;
    private JTextField departmentTextField;
    private EmployeeDAOImpl employeeDAOImpl;
    private TimeManagerApp timeManagerApp;
    private Employee previousEmployee = null;
    private boolean updateMode = false;

    public EmployeeDialog(TimeManagerApp theTimeManagerApp,
                          EmployeeDAOImpl theEmployeeDAOImpl, Employee thePreviousEmployee, boolean theUpdateMode) {
        this();
        employeeDAOImpl = theEmployeeDAOImpl;
        timeManagerApp = theTimeManagerApp;

        previousEmployee = thePreviousEmployee;
        updateMode = theUpdateMode;

        if (updateMode) {
            setTitle("Редактирование сотрудника");

            populateGui(previousEmployee);
        }
    }

    private void populateGui(Employee theEmployee) {

        nameTextField.setText(theEmployee.getName());
        departmentTextField.setText(theEmployee.getDepartment());
    }

    public EmployeeDialog(TimeManagerApp theTimeManagerApp, EmployeeDAOImpl theEmployeeDAOImpl) {
        this(theTimeManagerApp, theEmployeeDAOImpl, null, false);
    }

    public EmployeeDialog() {
        setTitle("Добавление пользователя");
        setBounds(100, 100, 450, 234);
        getContentPane().setLayout(new BorderLayout());
        contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        getContentPane().add(contentPanel, BorderLayout.CENTER);

        {
            JLabel lblName = new JLabel("ФИО");
            contentPanel.add(lblName, "2, 2, right, default");
        }
        {
            nameTextField = new JTextField();
            contentPanel.add(nameTextField, "4, 2, fill, default");
            nameTextField.setColumns(10);
        }
        {
            JLabel lblDepartment = new JLabel("Отдел");
            contentPanel.add(lblDepartment, "2, 4, right, default");
        }
        {
            departmentTextField = new JTextField();
            contentPanel.add(departmentTextField, "4, 4, fill, default");
            departmentTextField.setColumns(10);
        }
        {
            JPanel buttonPane = new JPanel();
            buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
            getContentPane().add(buttonPane, BorderLayout.SOUTH);
            {
                JButton okButton = new JButton("Сохранить");
                okButton.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        saveEmployee();
                    }
                });
                okButton.setActionCommand("ОК");
                buttonPane.add(okButton);
                getRootPane().setDefaultButton(okButton);
            }
            {
                JButton cancelButton = new JButton("Отменить");
                cancelButton.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        setVisible(false);
                        dispose();
                    }
                });
                cancelButton.setActionCommand("Отменить");
                buttonPane.add(cancelButton);
            }
        }
    }

    protected void saveEmployee() {

        String name = nameTextField.getText();
        String department = departmentTextField.getText();

        Employee tempEmployee = null;

        if (updateMode) {
            tempEmployee = previousEmployee;

            tempEmployee.setName(name);
            tempEmployee.setDepartment(department);

        } else {
            tempEmployee = new Employee(name, department);
        }

        try {
            if (updateMode) {
                employeeDAOImpl.update(tempEmployee);
            } else {
                employeeDAOImpl.insert(tempEmployee);
            }

            setVisible(false);
            dispose();

            timeManagerApp.refreshEmployeesView();

            JOptionPane.showMessageDialog(timeManagerApp,
                    "Сотрудник добавлен успешно", "Сохранение сотрудника",
                    JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception exc) {
            JOptionPane.showMessageDialog(timeManagerApp,
                    "Ошибка сохранения сотрудника: " + exc.getMessage(), "Ошибка",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}
