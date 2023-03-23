package presenter;

import model.Doctor;
import model.Locatie;
import model.PasswordEncryptor;
import org.javatuples.Pair;
import persistence.DoctorPersistence;
import persistence.LocatiePersistence;
import view.AdminView;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class AdminPresenter {
    private final AdminView adminView;
    private final PasswordEncryptor passwordEncryptor;

    public AdminPresenter(AdminView adminView, PasswordEncryptor passwordEncryptor) throws SQLException {
        this.adminView = adminView;
        this.passwordEncryptor = passwordEncryptor;

        this.adminView.updateDoctorTable(DoctorPersistence.readDoctori(""));
        this.adminView.updateLocatieTable(LocatiePersistence.readLocatii(""));
        this.adminView.addDoctorTableListSelectionListener(doctorListSelectionListener);
        this.adminView.addLocationTableListSelectionListener(locationListSelectionListener);

        this.adminView.addCreeazaButtonListener(creeazaButtonListener);
        this.adminView.addActualizeazaButtonListener(actualizeazaButtonListener);
        this.adminView.addStergeButtonListener(stergeButtonListener);
    }
    private final ListSelectionListener doctorListSelectionListener = new ListSelectionListener() {
        @Override
        public void valueChanged(ListSelectionEvent e) {
            if (adminView.getDoctorTableSelectedRow() >= 0) {
                adminView.setIdFieldText(adminView.getDoctorTableValueAt(0).toString());
                adminView.setUsernameFieldText(adminView.getDoctorTableValueAt(1).toString());
                adminView.setNumeFieldText(adminView.getDoctorTableValueAt(2).toString());
                adminView.setPrenumeFieldText(adminView.getDoctorTableValueAt(3).toString());
                adminView.setCnpFieldText(adminView.getDoctorTableValueAt(4).toString());
                adminView.setEmailFieldText(adminView.getDoctorTableValueAt(5).toString());
                adminView.setLocatieIdFieldValue(Integer.parseInt(adminView.getDoctorTableValueAt(6).toString()));
            }
        }
    };
    private final ListSelectionListener locationListSelectionListener = new ListSelectionListener() {
        @Override
        public void valueChanged(ListSelectionEvent e) {
            if (adminView.getLocationTableSelectedRow() >= 0) {
                adminView.setLocatieIdFieldValue(Integer.parseInt(adminView.getLocationTableValueAt(0).toString()));
            }
        }
    };
    private final ActionListener creeazaButtonListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            Pair<String, String> saltHash = passwordEncryptor.encryptPassword(adminView.getPasswordFieldText());
            try {
                DoctorPersistence.insertDoctor(new Doctor(
                        adminView.getUsernameFieldText(),
                        saltHash.getValue0(),
                        saltHash.getValue1(),
                        null,
                        adminView.getNumeFieldText(),
                        adminView.getPrenumeFieldText(),
                        adminView.getEmailFieldText(),
                        adminView.getCnpFieldText(),
                        new Locatie(
                                adminView.getLocatieIdFieldValue(),
                                null,
                                null,
                                null,
                                null,
                                0
                        )
                ));

                adminView.updateDoctorTable(DoctorPersistence.readDoctori(""));
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }
    };
    private final ActionListener actualizeazaButtonListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            Pair<String, String> saltHash = passwordEncryptor.encryptPassword(adminView.getPasswordFieldText());
            try {
                DoctorPersistence.updateDoctor(adminView.getUsernameFieldText(), new Doctor(
                                adminView.getUsernameFieldText(),
                                saltHash.getValue0(),
                                saltHash.getValue1(),
                                Integer.valueOf(adminView.getIdFieldText()),
                                adminView.getNumeFieldText(),
                                adminView.getPrenumeFieldText(),
                                adminView.getEmailFieldText(),
                                adminView.getCnpFieldText(),
                                new Locatie(
                                        adminView.getLocatieIdFieldValue(),
                                        null,
                                        null,
                                        null,
                                        null,
                                        0
                                )),
                        adminView.getPasswordFieldText().length() > 0);

                adminView.updateDoctorTable(DoctorPersistence.readDoctori(""));
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }
    };
    private final ActionListener stergeButtonListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                DoctorPersistence.deleteDoctor(Integer.parseInt(adminView.getIdFieldText()));
                adminView.updateDoctorTable(DoctorPersistence.readDoctori(""));
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }
    };
}
