package businessLayer;

import connection.ConnectionSingleton;
import model.Donator;
import model.Locatie;
import model.Programare;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DonatorBL {
    private static final Connection connection = ConnectionSingleton.instance().getConnection();

    public static List<Programare> readProgramari(Donator donator) throws SQLException {
        List<Programare> programareList = new ArrayList<>(); //p.id, d.nume, d.prenume, d.grupa_sanguina, p.data_programare, l.nume, l.adresa, l.ora_deschidere, l.ora_inchidere, p.confirmare
        String query = "SELECT p.id, p.data_programare, l.nume, l.adresa, l.ora_deschidere, l.ora_inchidere, p.confirmare FROM programari p " +
                "JOIN locatii l on l.id = p.id_locatie " +
                "JOIN donatori_programari dp on p.id = dp.id_programare " +
                "JOIN donatori d on d.id = dp.id_donator " +
                "WHERE d.id = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, donator.getId());
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            programareList.add(new Programare(
                    resultSet.getInt("p.id"),
                    donator,
                    new Locatie(
                            null,
                            resultSet.getString("l.nume"),
                            resultSet.getString("l.adresa"),
                            resultSet.getTime("l.ora_deschidere").toLocalTime(),
                            resultSet.getTime("l.ora_inchidere").toLocalTime(),
                            0
                    ),
                    resultSet.getDate("p.data_programare").toLocalDate(),
                    resultSet.getBoolean("p.confirmare")
            ));
        }
        return programareList;
    }

    public static void creeazaProgramare(Programare programare) throws SQLException {
        connection.setAutoCommit(false);
        try {
            String query = "INSERT INTO programari VALUE (null, ?, ?, false)";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, programare.getLocatie().getId());
            statement.setDate(2, Date.valueOf(programare.getDataProgramarii()));
            statement.execute();

            query = "SELECT LAST_INSERT_ID()";
            statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();

            query = "INSERT INTO donatori_programari VALUE (?, ?)";
            statement = connection.prepareStatement(query);
            statement.setInt(1, programare.getDonator().getId());
            statement.setInt(2, resultSet.getInt(1));
            statement.execute();

            connection.commit();
        } catch (SQLException e) {
            connection.rollback();
            e.printStackTrace();
        }
        connection.setAutoCommit(true);
    }

    public static void deleteProgramare(int programareId) throws SQLException {
        String query = "DELETE FROM programari WHERE id = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, programareId);
        statement.execute();
    }
}
