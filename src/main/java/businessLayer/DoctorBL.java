package businessLayer;

import connection.ConnectionSingleton;
import model.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DoctorBL {
    private static final Connection connection = ConnectionSingleton.instance().getConnection();
    public static List<Programare> readProgramariDoctor(Doctor doctor) throws SQLException {
        List<Programare> programareList = new ArrayList<>();
        String query =  "SELECT p.id, d2.nume, d2.prenume, d2.grupa_sanguina, l.nume, p.data_programare, p.confirmare " +
                "FROM doctori d " +
                "JOIN locatii l on d.id_locatie = l.id " +
                "JOIN programari p on l.id = p.id_locatie " +
                "JOIN donatori_programari dp on p.id = dp.id_programare " +
                "JOIN donatori d2 on d2.id = dp.id_donator " +
                "WHERE d.id = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, doctor.getId());
        ResultSet resultSet = statement.executeQuery();
        while(resultSet.next()){
            programareList.add(new Programare(
                    resultSet.getInt("p.id"),
                    new Donator(
                            null,
                            null,
                            null,
                            null,
                            resultSet.getString("d2.nume"),
                            resultSet.getString("d2.prenume"),
                            GrupaSanguina.fromInteger(resultSet.getInt("d2.grupa_sanguina"))
                    ),
                    new Locatie(
                            null,
                            resultSet.getString("l.nume"),
                            null,
                            null,
                            null,
                            0
                    ),
                    resultSet.getDate("p.data_programare").toLocalDate(),
                    resultSet.getBoolean("p.confirmare")
            ));
        }
        return programareList;
    }

    public static void confirmaProgramare(Programare programare) throws SQLException {
        String query = "UPDATE programari SET confirmare = true WHERE id = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, programare.getId());
        statement.execute();
    }
}
