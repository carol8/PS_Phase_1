package view;

import model.Doctor;
import model.Locatie;

import javax.swing.*;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.event.ActionListener;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

public class AdminView extends JFrame {
    private JPanel mainPanel;
    private JTable doctorTable;
    private JTable locationTable;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton creeazaButton;
    private JButton actualizeazaButton;
    private JButton stergeButton;
    private JTextField numeField;
    private JTextField prenumeField;
    private JTextField cnpField;
    private JTextField emailField;
    private JSpinner locatieIdSpinner;
    private JTextField idField;

    public AdminView() {
        this.setContentPane(this.mainPanel);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.pack();
        this.setSize(this.mainPanel.getSize());
        this.setMinimumSize(this.mainPanel.getMinimumSize());
    }

    public void addCreeazaButtonListener(ActionListener listener) {
        creeazaButton.addActionListener(listener);
    }

    public void addActualizeazaButtonListener(ActionListener listener) {
        actualizeazaButton.addActionListener(listener);
    }

    public void addStergeButtonListener(ActionListener listener) {
        stergeButton.addActionListener(listener);
    }

    public void addLocationTableListSelectionListener(ListSelectionListener listener) {
        locationTable.getSelectionModel().addListSelectionListener(listener);
    }

    public void addDoctorTableListSelectionListener(ListSelectionListener listener) {
        doctorTable.getSelectionModel().addListSelectionListener(listener);
    }

    public void updateLocatieTable(List<Locatie> locatii) {
        updateTable(locationTable, Arrays.asList(locatii.toArray()));
    }

    public void updateDoctorTable(List<Doctor> doctori) {
        String[] header = {"id", "username", "nume", "prenume", "CNP", "email", "locatie"};
        String[][] data = new String[doctori.size()][header.length];
        for (int i = 0; i < doctori.size(); i++) {
            data[i][0] = doctori.get(i).getId().toString();
            data[i][1] = doctori.get(i).getUsername();
            data[i][2] = doctori.get(i).getNume();
            data[i][3] = doctori.get(i).getPrenume();
            data[i][4] = doctori.get(i).getCnp();
            data[i][5] = doctori.get(i).getEmail();
            data[i][6] = doctori.get(i).getLocatie().toString();
        }
        TableModel tableModel = new DefaultTableModel(data, header);
        doctorTable.setModel(tableModel);
        repaint();
    }

    public int getDoctorTableSelectedRow() {
        return doctorTable.getSelectedRow();
    }

    public int getLocationTableSelectedRow() {
        return locationTable.getSelectedRow();
    }

    public Object getDoctorTableValueAt(int col) {
        return doctorTable.getValueAt(doctorTable.getSelectedRow(), col);
    }

    public Object getLocationTableValueAt(int col) {
        return locationTable.getValueAt(locationTable.getSelectedRow(), col);
    }

    public String getIdFieldText() {
        return idField.getText();
    }

    public void setIdFieldText(String text) {
        idField.setText(text);
    }

    public String getUsernameFieldText() {
        return usernameField.getText();
    }

    public void setUsernameFieldText(String text) {
        usernameField.setText(text);
    }

    public String getPasswordFieldText() {
        return String.valueOf(passwordField.getPassword());
    }

    public String getNumeFieldText() {
        return numeField.getText();
    }

    public void setNumeFieldText(String text) {
        numeField.setText(text);
    }

    public String getPrenumeFieldText() {
        return prenumeField.getText();
    }

    public void setPrenumeFieldText(String text) {
        prenumeField.setText(text);
    }

    public String getCnpFieldText() {
        return cnpField.getText();
    }

    public void setCnpFieldText(String text) {
        cnpField.setText(text);
    }

    public String getEmailFieldText() {
        return emailField.getText();
    }

    public void setEmailFieldText(String text) {
        emailField.setText(text);
    }

    public int getLocatieIdFieldValue() {
        return (int) locatieIdSpinner.getValue();
    }

    public void setLocatieIdFieldValue(int value) {
        locatieIdSpinner.setValue(value);
    }

    private void updateTable(JTable table, List<Object> objects) {
        String[] header;
        String[][] data;
        header = new String[objects.get(0).getClass().getDeclaredFields().length];
        data = new String[objects.size()][objects.get(0).getClass().getDeclaredFields().length];
        for (int i = 0; i < objects.get(0).getClass().getDeclaredFields().length; i++) {
            header[i] = objects.get(0).getClass().getDeclaredFields()[i].getName();
        }
        for (int i = 0; i < objects.size(); i++) {
            for (int j = 0; j < objects.get(0).getClass().getDeclaredFields().length; j++) {
                Field field = objects.get(0).getClass().getDeclaredFields()[j];
                field.setAccessible(true);
                try {
                    data[i][j] = field.get(objects.get(i)).toString();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        TableModel tableModel = new DefaultTableModel(data, header);
        table.setModel(tableModel);
        repaint();
    }

    private void createUIComponents() {
        locatieIdSpinner = new JSpinner(new SpinnerNumberModel(0, 0, Integer.MAX_VALUE, 1));
    }
}
