package view;

import javax.swing.*;
import java.awt.event.ActionListener;

public class LoginView extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton inregistrareButton;
    private JButton autentificareButton;
    private JPanel mainPanel;
    private JLabel statusAutentificareLabel;

    public LoginView() {
        this.setContentPane(this.mainPanel);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.pack();
        this.setSize(this.mainPanel.getSize());
        this.setResizable(false);
    }

    public void addInregistrareButtonListener(ActionListener actionListener) {
        inregistrareButton.addActionListener(actionListener);
    }

    public void addAutentificareButtonListener(ActionListener actionListener) {
        autentificareButton.addActionListener(actionListener);
    }

    public String getUsernameFieldText() {
        return usernameField.getText();
    }

    public String getPasswordFieldText() {
        return String.valueOf(passwordField.getPassword());
    }

    public void setStatusAutentificareLabelText(String text){
        statusAutentificareLabel.setText(text);
    }

    public void setStatusAutentificareLabelVisible(boolean visible){
        statusAutentificareLabel.setVisible(visible);
    }
}
