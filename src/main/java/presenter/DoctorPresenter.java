package presenter;

import businessLayer.DoctorBL;
import model.Doctor;
import model.Programare;
import persistence.DoctorPersistence;
import view.DoctorView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class DoctorPresenter {
    private final DoctorView doctorView;
    private final Doctor doctor;

    public DoctorPresenter(DoctorView doctorView, Doctor doctor) throws SQLException {
        this.doctorView = doctorView;
        this.doctor = doctor;

        this.doctorView.addConfirmareButtonListener(confirmareButtonListener);

        this.doctorView.updateTableProgramari(DoctorBL.readProgramariDoctor(doctor));
    }

    private final ActionListener confirmareButtonListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if(doctorView.getTableProgramariSelectedRow() >= 0) {
                try {
                    DoctorBL.confirmaProgramare(new Programare(
                            Integer.parseInt(doctorView.getTableProgramariValueAt(0).toString()),
                            null,
                            null,
                            null,
                            Boolean.parseBoolean(doctorView.getTableProgramariValueAt(6).toString())
                    ));

                    doctorView.updateTableProgramari(DoctorBL.readProgramariDoctor(doctor));
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        }
    };
}
