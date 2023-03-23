package view;

import com.github.lgooddatepicker.components.DatePicker;
import model.GrupaSanguina;
import model.Locatie;
import model.Programare;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.List;

public class DonatorView extends JFrame{
    private JTable tableLocatii;
    private JTable tableProgramari;
    private JTextField usernameField;
    private JComboBox<GrupaSanguina> grupaSanguinaComboBox;
    private JButton actualizeazaDateButton;
    private JButton stergeContButton;

    private JPasswordField passwordField;
    private JButton programeazaMaButton;
    private JButton stergeProgramareaButton;
    private JPanel mainPanel;
    private JTextField numeField;
    private JTextField prenumeField;
    private JLabel statusProgramareLabel;
    private DatePicker programareDatePicker;
    private JLabel statusContLabel;

    public DonatorView() {
        this.setContentPane(this.mainPanel);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.pack();
        this.setSize(this.mainPanel.getSize());
        this.setMinimumSize(this.mainPanel.getMinimumSize());
    }

    public String getUsernameFieldText(){
        return usernameField.getText();
    }
    public String getPasswordFieldText(){
        return String.valueOf(passwordField.getPassword());
    }
    public String getNumeFieldText(){
        return numeField.getText();
    }
    public String getPrenumeFieldText(){
        return prenumeField.getText();
    }
    public GrupaSanguina getGrupaSanguinaComboBoxValue(){
        return (GrupaSanguina) grupaSanguinaComboBox.getSelectedItem();
    }
    public LocalDate getProgramareDatePickerValue(){
        return programareDatePicker.getDate();
    }
    public int getTableLocatiiSelectedRow(){
        return tableLocatii.getSelectedRow();
    }

    public Object getTableLocatiiValueAt(int col) {
        return tableLocatii.getValueAt(tableLocatii.getSelectedRow(), col);
    }

    public int getTableProgramariSelectedRow(){
        return tableProgramari.getSelectedRow();
    }

    public Object getTableProgramariValueAt(int col) {
        return tableProgramari.getValueAt(tableProgramari.getSelectedRow(), col);
    }

    public void setUsernameFieldText(String text){
        usernameField.setText(text);
    }
    public void setNumeFieldText(String text){
        numeField.setText(text);
    }
    public void setPrenumeFieldText(String text){
        prenumeField.setText(text);
    }
    public void setGrupaSanguinaComboBoxValue(GrupaSanguina grupaSanguina){
        grupaSanguinaComboBox.setSelectedItem(grupaSanguina);
    }
    public void setStatusProgramareLabelText(String text){
        statusProgramareLabel.setText(text);
    }
    public void setStatusProgramareLabelVisible(boolean visible){
        statusProgramareLabel.setVisible(visible);
    }
    public void setStatusProgramareLabelColor(Color color){
        statusProgramareLabel.setForeground(color);
    }
    public void setStatusContLabelText(String text){
        statusContLabel.setText(text);
    }
    public void setStatusContLabelVisible(boolean visible){
        statusContLabel.setVisible(visible);
    }

    public void addActualizeazaDateListener(ActionListener actionListener){
        actualizeazaDateButton.addActionListener(actionListener);
    }

    public void addStergeContActionListener(ActionListener actionListener){
        stergeContButton.addActionListener(actionListener);
    }

    public void addProgrameazaMaButtonListener(ActionListener actionListener){
        programeazaMaButton.addActionListener(actionListener);
    }

    public void addStergeProgramareaButtonListener(ActionListener actionListener){
        stergeProgramareaButton.addActionListener(actionListener);
    }

    public void updateTableProgramari(List<Programare> programareList){
        //p.id, d.nume, d.prenume, d.grupa_sanguina, p.data_programare, l.nume, l.adresa, l.ora_deschidere, l.ora_inchidere, p.confirmare
        String[] header = {"id", "nume donator", "prenume donator", "grupa sanguina", "data programare", "nume locatie", "adresa locatie", "ora deschiderii", "ora inchiderii", "confirmare"};
        String[][] data = new String[programareList.size()][header.length];
        for (int i = 0; i < programareList.size(); i++) {
            data[i][0] = programareList.get(i).getId().toString();
            data[i][1] = programareList.get(i).getDonator().getNume();
            data[i][2] = programareList.get(i).getDonator().getPrenume();
            data[i][3] = String.valueOf(programareList.get(i).getDonator().getGrupaSanguina());
            data[i][4] = programareList.get(i).getDataProgramarii().toString();
            data[i][5] = programareList.get(i).getLocatie().getNume();
            data[i][6] = programareList.get(i).getLocatie().getAdresa();
            data[i][7] = programareList.get(i).getLocatie().getOraDeschidere().toString();
            data[i][8] = programareList.get(i).getLocatie().getOraInchidere().toString();
            data[i][9] = programareList.get(i).isConfirmare() ? "Confirmat" : "Neconfirmat";
        }
        TableModel tableModel = new DefaultTableModel(data, header);
        tableProgramari.setModel(tableModel);
        repaint();
    }

    public void updateTableLocatii(List<Locatie> locatieList){
        String[] header = {"id", "nume", "adresa", "ora deschidere", "ora inchidere", "numar maxim programari"};
        String[][] data = new String[locatieList.size()][header.length];
        for (int i = 0; i < locatieList.size(); i++) {
            data[i][0] = locatieList.get(i).getId().toString();
            data[i][1] = locatieList.get(i).getNume();
            data[i][2] = locatieList.get(i).getAdresa();
            data[i][3] = locatieList.get(i).getOraDeschidere().toString();
            data[i][4] = locatieList.get(i).getOraInchidere().toString();
            data[i][5] = String.valueOf(locatieList.get(i).getNumarMaximRecoltari());
        }
        TableModel tableModel = new DefaultTableModel(data, header);
        tableLocatii.setModel(tableModel);
        repaint();
    }

    private void createUIComponents() {
        grupaSanguinaComboBox = new JComboBox<>(GrupaSanguina.values());
    }
}
