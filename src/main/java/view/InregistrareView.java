package view;

import model.GrupaSanguina;

import javax.swing.*;
import java.awt.event.ActionListener;

public class InregistrareView extends JFrame{
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JPasswordField repeatPasswordField;
    private JTextField numeField;
    private JTextField prenumeField;
    private JComboBox<GrupaSanguina> grupaSanguinaComboBox;
    private JButton inregistrareButton;
    private JPanel mainPanel;
    private JLabel eroareInregistrareLabel;

    public InregistrareView(){
        this.setContentPane(this.mainPanel);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.pack();
        this.setSize(this.mainPanel.getSize());
        this.setResizable(false);
    }

    public void addinregistrareButtonListener(ActionListener actionListener){
        inregistrareButton.addActionListener(actionListener);
    }

    public String getUsernameFieldText(){
        return usernameField.getText();
    }

    public String getPasswordFieldText(){
        return String.valueOf(passwordField.getPassword());
    }

    public String getRepeatPasswordFieldText(){
        return String.valueOf(repeatPasswordField.getPassword());
    }

    public String getNumeFieldText(){
        return numeField.getText();
    }

    public String getPrenumeFieldText(){
        return prenumeField.getText();
    }

    public GrupaSanguina getGrupaSanguinaValue(){
        return (GrupaSanguina) grupaSanguinaComboBox.getSelectedItem();
    }

    public void setEroareInregistrareLabelVisible(boolean visible){
        eroareInregistrareLabel.setVisible(visible);
    }

    public void setEroareInregistrareLabelText(String text){
        eroareInregistrareLabel.setText(text);
    }

    private void createUIComponents() {
        grupaSanguinaComboBox = new JComboBox<>(GrupaSanguina.values());
    }
}
