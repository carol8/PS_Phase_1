import constants.SareConstants;
import model.PasswordEncryptor;
import presenter.LoginPresenter;
import view.LoginView;

import javax.crypto.SecretKeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class Main {
    public static void main(String[] args) throws NoSuchAlgorithmException {
        PasswordEncryptor passwordEncryptor = new PasswordEncryptor(
                SareConstants.SARE_LENGTH,
                SareConstants.SARE_ACCEPTED_CHARS,
                SecureRandom.getInstanceStrong(),
                SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1")
        );
        LoginView loginView = new LoginView();
        LoginPresenter loginPresenter = new LoginPresenter(loginView, passwordEncryptor);
        loginView.setVisible(true);
    }
}
