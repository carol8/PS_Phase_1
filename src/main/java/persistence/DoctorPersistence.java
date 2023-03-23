package persistence;

import connection.ConnectionSingleton;
import model.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DoctorPersistence {
    private static final Connection connection = ConnectionSingleton.instance().getConnection();

    public static void insertDoctor(Doctor doctor) throws SQLException {
        connection.setAutoCommit(false);
        try {
            String query = "INSERT INTO doctori VALUE (null, ?, ?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, doctor.getNume());
            statement.setString(2, doctor.getPrenume());
            statement.setString(3, doctor.getEmail());
            statement.setString(4, doctor.getCnp());
            statement.setInt(5, doctor.getLocatie().getId());
            statement.execute();

            query = "SELECT LAST_INSERT_ID()";
            statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();

            query = "INSERT INTO utilizatori VALUE (?, ?, ?, false, ?, null)";
            statement = connection.prepareStatement(query);
            statement.setString(1, doctor.getUsername());
            statement.setString(2, doctor.getSare());
            statement.setString(3, doctor.getPasswordHash());
            statement.setInt(4, resultSet.getInt(1));
            statement.execute();

            connection.commit();
        } catch (SQLException e) {
            connection.rollback();
            e.printStackTrace();
        }
        connection.setAutoCommit(true);
    }

    public static List<Doctor> readDoctori(String conditions) throws SQLException {
        List<Doctor> result = new ArrayList<>();
        String query;
        if (conditions.length() == 0) {
            query = "SELECT u.username, u.sare, u.hash_parola, d.id, d.nume, d.prenume, d.email, d.cnp, l.id, l.nume, l.adresa, l.ora_deschidere, l.ora_inchidere, l.numar_maxim_recoltari " +
                    "FROM utilizatori u " +
                    "JOIN doctori d on u.id_doctor = d.id " +
                    "JOIN locatii l on d.id_locatie = l.id";
        } else {
            query = "SELECT u.username, u.sare, u.hash_parola, d.id, d.nume, d.prenume, d.email, d.cnp, l.id, l.nume, l.adresa, l.ora_deschidere, l.ora_inchidere, l.numar_maxim_recoltari " +
                    "FROM utilizatori u " +
                    "JOIN doctori d on u.id_doctor = d.id " +
                    "JOIN locatii l on d.id_locatie = l.id " +
                    "WHERE " + conditions;
        }
        PreparedStatement statement = connection.prepareStatement(query);
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            result.add(new Doctor(resultSet.getString("username"),
                    resultSet.getString("sare"),
                    resultSet.getString("hash_parola"),
                    resultSet.getInt("d.id"),
                    resultSet.getString("d.nume"),
                    resultSet.getString("prenume"),
                    resultSet.getString("email"),
                    resultSet.getString("cnp"),
                    new Locatie(resultSet.getInt("l.id"),
                            resultSet.getString("l.nume"),
                            resultSet.getString("adresa"),
                            resultSet.getTime("ora_deschidere").toLocalTime(),
                            resultSet.getTime("ora_inchidere").toLocalTime(),
                            resultSet.getInt("numar_maxim_recoltari"))
            ));
        }
        return result;
    }

    public static void updateDoctor(String usernameDoctorVechi, Doctor doctorNou, boolean updatePassword) throws SQLException {
        connection.setAutoCommit(false);
        try {
            Doctor doctorVechi = readDoctori("username = \"" + usernameDoctorVechi + "\"").get(0);
            String query = null;
            PreparedStatement statement = null;
            if(updatePassword) {
                query = "UPDATE utilizatori SET sare = ?, hash_parola = ? WHERE username = ?";
                statement = connection.prepareStatement(query);
                statement.setString(1, doctorNou.getSare());
                statement.setString(2, doctorNou.getPasswordHash());
                statement.setString(3, usernameDoctorVechi);
                statement.execute();
            }

            query = "UPDATE doctori SET nume = ?, prenume = ?, email = ?, cnp = ?, id_locatie = ? WHERE id = ?";
            statement = connection.prepareStatement(query);
            statement.setString(1, doctorNou.getNume());
            statement.setString(2, doctorNou.getPrenume());
            statement.setString(3, doctorNou.getEmail());
            statement.setString(4, doctorNou.getCnp());
            statement.setInt(5, doctorNou.getLocatie().getId());
            statement.setInt(6, doctorVechi.getId());
            statement.execute();

            connection.commit();
        } catch (SQLException e) {
            connection.rollback();
            e.printStackTrace();
        }
        connection.setAutoCommit(true);
    }

    public static void deleteDoctor(int doctorId) throws SQLException {
        String query = "DELETE FROM doctori WHERE id = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, doctorId);
        statement.execute();
    }
}
