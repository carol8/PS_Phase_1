package presenter;

import model.*;
import persistence.AdminPersistence;
import persistence.DoctorPersistence;
import persistence.DonatorPersistence;
import persistence.UserPersistence;
import view.*;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.awt.event.ActionListener;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.sql.SQLException;
import java.util.List;

public class LoginPresenter {
    private LoginView loginView;
    private PasswordEncryptor passwordEncryptor;

    private final ActionListener inregistrareActionListener = e -> {
        InregistrareView inregistrareView = new InregistrareView();
        InregistrarePresenter inregistrarePresenter = new InregistrarePresenter(inregistrareView, passwordEncryptor);
        inregistrareView.setVisible(true);
    };

    private final ActionListener autentificareActionListener = e -> {
        try {
            List<User> userList = UserPersistence.readUseri("");
            if(userList.stream().anyMatch(user1 -> user1.getUsername().equals(loginView.getUsernameFieldText()))) {
                User user = userList.stream()
                        .filter(user1 -> user1.getUsername().equals(loginView.getUsernameFieldText()))
                        .toList()
                        .get(0);

                String inputPasswordHash = passwordEncryptor.encryptPassword(loginView.getPasswordFieldText(), user.getSare());
                if (inputPasswordHash.equals(user.getPasswordHash())) {
                    List<Admin> admins = AdminPersistence.readAdmins("username = \"" + user.getUsername() + "\"");
                    List<Doctor> doctors = DoctorPersistence.readDoctori("username = \"" + user.getUsername() + "\"");
                    List<Donator> donators = DonatorPersistence.readDonatori("username = \"" + user.getUsername() + "\"");
                    if (admins.size() > 0) {
                        AdminView adminView = new AdminView();
                        AdminPresenter adminPresenter = new AdminPresenter(adminView, admins.get(0), passwordEncryptor);
                        adminView.setVisible(true);
                        loginView.dispose();
                    } else if (doctors.size() > 0) {
                        DoctorView doctorView = new DoctorView();
                        DoctorPresenter doctorPresenter = new DoctorPresenter(doctorView, doctors.get(0));
                        doctorView.setVisible(true);
                        loginView.dispose();
                    } else if (donators.size() > 0) {
                        DonatorView donatorView = new DonatorView();
                        DonatorPresenter donatorPresenter = new DonatorPresenter(donatorView, donators.get(0), passwordEncryptor);
                        donatorView.setVisible(true);
                        loginView.dispose();
                    }
                }
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    };

    public LoginPresenter(LoginView loginView, PasswordEncryptor passwordEncryptor){
        this.loginView = loginView;
        this.passwordEncryptor = passwordEncryptor;
        this.loginView.addInregistrareButtonListener(inregistrareActionListener);
        this.loginView.addAutentificareButtonListener(autentificareActionListener);
    }
}
