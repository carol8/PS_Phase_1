package presenter;

import businessLayer.DonatorBL;
import model.Donator;
import model.Locatie;
import model.PasswordEncryptor;
import model.Programare;
import org.javatuples.Pair;
import persistence.DonatorPersistence;
import persistence.LocatiePersistence;
import view.DonatorView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;

public class DonatorPresenter {
    private final DonatorView donatorView;
    private final PasswordEncryptor passwordEncryptor;
    private Donator donator;
    private final ActionListener actualizeazaDateListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (JOptionPane.showConfirmDialog(donatorView, "Sigur doresti sa iti modifici datele?") == 0) {
                Pair<String, String> sareHash = passwordEncryptor.encryptPassword(donatorView.getPasswordFieldText());
                try {
                    Donator donatorNou = new Donator(
                            donatorView.getUsernameFieldText(),
                            sareHash.getValue0(),
                            sareHash.getValue1(),
                            null,
                            donatorView.getNumeFieldText(),
                            donatorView.getPrenumeFieldText(),
                            donatorView.getGrupaSanguinaComboBoxValue()
                    );
                    DonatorPersistence.updateDonator(donatorView.getUsernameFieldText(), donatorNou, donatorView.getPasswordFieldText().length() > 0);
                    donator = donatorNou;
                    donatorView.setStatusContLabelText("Date actualizate!");
                    donatorView.setStatusContLabelVisible(true);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        }
    };
    private final ActionListener stergeContActionListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (JOptionPane.showConfirmDialog(donatorView, "Sigur doresti sa iti stergi contul?") == 0) {
                try {
                    DonatorPersistence.deleteDonator(donator.getId());
                    donatorView.dispose();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        }
    };
    private final ActionListener programeazaMaActionListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (JOptionPane.showConfirmDialog(donatorView, "Sigur doresti sa te programezi?") == 0) {
                if (donatorView.getTableLocatiiSelectedRow() < 0) {
                    donatorView.setStatusProgramareLabelColor(new Color(127, 0, 0));
                    donatorView.setStatusProgramareLabelText("Trebuie sa selectezi o locatie pentru a te programa!");
                    donatorView.setStatusProgramareLabelVisible(true);
                } else if (donatorView.getProgramareDatePickerValue() == null) {
                    donatorView.setStatusProgramareLabelColor(new Color(127, 0, 0));
                    donatorView.setStatusProgramareLabelText("Trebuie sa selectezi o data pentru a te programa!");
                    donatorView.setStatusProgramareLabelVisible(true);
                } else if (donatorView.getProgramareDatePickerValue().compareTo(LocalDate.now()) < 0) {
                    donatorView.setStatusProgramareLabelColor(new Color(127, 0, 0));
                    donatorView.setStatusProgramareLabelText("Nu te poti programa in trecut!");
                    donatorView.setStatusProgramareLabelVisible(true);
                } else {
                    Locatie locatie = new Locatie(
                            Integer.parseInt(donatorView.getTableLocatiiValueAt(0).toString()),
                            donatorView.getTableLocatiiValueAt(1).toString(),
                            donatorView.getTableLocatiiValueAt(2).toString(),
                            LocalTime.parse(donatorView.getTableLocatiiValueAt(3).toString()),
                            LocalTime.parse(donatorView.getTableLocatiiValueAt(4).toString()),
                            Integer.parseInt(donatorView.getTableLocatiiValueAt(5).toString())
                    );
                    Programare programare = new Programare(null, donator, locatie, donatorView.getProgramareDatePickerValue(), false);
                    try {
                        DonatorBL.creeazaProgramare(programare);
                        donatorView.updateTableProgramari(DonatorBL.readProgramari(donator));

                        donatorView.setStatusProgramareLabelColor(new Color(0, 127, 0));
                        donatorView.setStatusProgramareLabelText("Programare efectuata cu succes!");
                        donatorView.setVisible(true);
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        }
    };
    private final ActionListener stergeProgramareaActionListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (JOptionPane.showConfirmDialog(donatorView, "Sigur doresti sa stergi programarea?") == 0) {
                if (donatorView.getTableProgramariSelectedRow() < 0) {
                    donatorView.setStatusProgramareLabelColor(new Color(127, 0, 0));
                    donatorView.setStatusProgramareLabelText("Trebuie sa selectezi o programare pentru a o sterge!");
                    donatorView.setStatusProgramareLabelVisible(true);
                } else if (LocalDate.parse(donatorView.getTableProgramariValueAt(4).toString()).compareTo(LocalDate.now()) < 0) {
                    donatorView.setStatusProgramareLabelColor(new Color(127, 0, 0));
                    donatorView.setStatusProgramareLabelText("Nu poti sa stergi o programare din trecut!");
                    donatorView.setStatusProgramareLabelVisible(true);
                } else {
                    try {
                        DonatorBL.deleteProgramare(Integer.parseInt(donatorView.getTableProgramariValueAt(0).toString()));
                        donatorView.updateTableProgramari(DonatorBL.readProgramari(donator));

                        donatorView.setStatusProgramareLabelColor(new Color(0, 127, 0));
                        donatorView.setStatusProgramareLabelText("Programare stearsa cu succes!");
                        donatorView.setStatusProgramareLabelVisible(true);
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        }
    };

    public DonatorPresenter(DonatorView donatorView, Donator donator, PasswordEncryptor passwordEncryptor) throws SQLException {
        this.donatorView = donatorView;
        this.donator = donator;
        this.passwordEncryptor = passwordEncryptor;

        this.donatorView.setUsernameFieldText(donator.getUsername());
        this.donatorView.setNumeFieldText(donator.getNume());
        this.donatorView.setPrenumeFieldText(donator.getPrenume());
        this.donatorView.setGrupaSanguinaComboBoxValue(donator.getGrupaSanguina());

        this.donatorView.addActualizeazaDateListener(actualizeazaDateListener);
        this.donatorView.addStergeContActionListener(stergeContActionListener);
        this.donatorView.addProgrameazaMaButtonListener(programeazaMaActionListener);
        this.donatorView.addStergeProgramareaButtonListener(stergeProgramareaActionListener);

        this.donatorView.updateTableLocatii(LocatiePersistence.readLocatii(""));
        this.donatorView.updateTableProgramari(DonatorBL.readProgramari(donator));
    }
}
