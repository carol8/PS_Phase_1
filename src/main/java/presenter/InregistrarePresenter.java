package presenter;

import model.Donator;
import model.PasswordEncryptor;
import org.javatuples.Pair;
import persistence.DonatorPersistence;
import view.InregistrareView;

import java.awt.event.ActionListener;
import java.sql.SQLException;

public class InregistrarePresenter {
    private InregistrareView inregistrareView;
    private PasswordEncryptor passwordEncryptor;
    private final ActionListener inregistrareButtonListener = e -> {
        if (inregistrareView.getUsernameFieldText().length() == 0) {
            inregistrareView.setEroareInregistrareLabelText("Username-ul nu poate fi gol!");
            inregistrareView.setEroareInregistrareLabelVisible(true);
        } else if (inregistrareView.getPasswordFieldText().length() == 0) {
            inregistrareView.setEroareInregistrareLabelText("Parola nu poate fi goala!");
            inregistrareView.setEroareInregistrareLabelVisible(true);
        } else if (inregistrareView.getRepeatPasswordFieldText().length() == 0) {
            inregistrareView.setEroareInregistrareLabelText("Parola trebuie repetata!");
            inregistrareView.setEroareInregistrareLabelVisible(true);
        } else if (!inregistrareView.getRepeatPasswordFieldText().equals(inregistrareView.getPasswordFieldText())) {
            inregistrareView.setEroareInregistrareLabelText("Parolele nu sunt identice!");
            inregistrareView.setEroareInregistrareLabelVisible(true);
        } else if (inregistrareView.getNumeFieldText().length() == 0) {
            inregistrareView.setEroareInregistrareLabelText("Numele nu poate fi gol!");
            inregistrareView.setEroareInregistrareLabelVisible(true);
        } else if (inregistrareView.getPrenumeFieldText().length() == 0) {
            inregistrareView.setEroareInregistrareLabelText("Prenumele nu poate fi gol!");
            inregistrareView.setEroareInregistrareLabelVisible(true);
        } else {
            try {
                Pair<String, String> saltHash = passwordEncryptor.encryptPassword(inregistrareView.getPasswordFieldText());
                DonatorPersistence.insertDonator(new Donator(
                        inregistrareView.getUsernameFieldText(),
                        saltHash.getValue0(),
                        saltHash.getValue1(),
                        null,
                        inregistrareView.getNumeFieldText(),
                        inregistrareView.getPrenumeFieldText(),
                        inregistrareView.getGrupaSanguinaValue()
                ));

                inregistrareView.dispose();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }
    };

    public InregistrarePresenter(InregistrareView inregistrareView, PasswordEncryptor passwordEncryptor) {
        this.passwordEncryptor = passwordEncryptor;
        this.inregistrareView = inregistrareView;
        this.inregistrareView.addinregistrareButtonListener(inregistrareButtonListener);
    }
}
