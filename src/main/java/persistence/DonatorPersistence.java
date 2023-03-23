package persistence;

import connection.ConnectionSingleton;
import model.Doctor;
import model.Donator;
import model.GrupaSanguina;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DonatorPersistence {
    private static final Connection connection = ConnectionSingleton.instance().getConnection();

    public static void insertDonator(Donator donator) throws SQLException {
        connection.setAutoCommit(false);
        try {
            String query = "INSERT INTO donatori VALUE (null, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, donator.getNume());
            statement.setString(2, donator.getPrenume());
            statement.setInt(3, donator.getGrupaSanguina().toInteger());
            statement.execute();

            String query2 = "SELECT LAST_INSERT_ID()";
            statement = connection.prepareStatement(query2);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();

            String query3 = "INSERT INTO utilizatori VALUE (?, ?, ?, false, null, ?)";
            statement = connection.prepareStatement(query3);
            statement.setString(1, donator.getUsername());
            statement.setString(2, donator.getSare());
            statement.setString(3, donator.getPasswordHash());
            statement.setInt(4, resultSet.getInt(1));
            statement.execute();

            connection.commit();
        } catch (SQLException e){
            connection.rollback();
            e.printStackTrace();
        }
        connection.setAutoCommit(true);
    }

    public static List<Donator> readDonatori(String conditions) throws SQLException {
        List<Donator> result = new ArrayList<>();
        String query;
        if(conditions.length() == 0){
            query = "SELECT u.username, u.sare, u.hash_parola, d.id, d.nume, d.prenume, d.grupa_sanguina " +
                    "FROM utilizatori u " +
                    "JOIN donatori d on u.id_donator = d.id";
        }
        else{
            query = "SELECT u.username, u.sare, u.hash_parola, d.id, d.nume, d.prenume, d.grupa_sanguina " +
                    "FROM utilizatori u " +
                    "JOIN donatori d on u.id_donator = d.id " +
                    "WHERE " + conditions;
        }
        PreparedStatement statement = connection.prepareStatement(query);
        ResultSet resultSet = statement.executeQuery();
        while(resultSet.next()){
            result.add(new Donator(resultSet.getString("username"),
                    resultSet.getString("sare"),
                    resultSet.getString("hash_parola"),
                    resultSet.getInt("id"),
                    resultSet.getString("nume"),
                    resultSet.getString("prenume"),
                    GrupaSanguina.fromInteger(resultSet.getInt("grupa_sanguina"))));
        }
        return result;
    }

    public static void updateDonator(String usernameDoctorVechi, Donator donatorNou, boolean updatePassword) throws SQLException {
        connection.setAutoCommit(false);
        try {
            Donator donatorVechi = readDonatori("username = \"" + usernameDoctorVechi + "\"").get(0);
            String query = null;
            PreparedStatement statement = null;
            if(updatePassword) {
                query = "UPDATE utilizatori SET sare = ?, hash_parola = ? WHERE username = ?";
                statement = connection.prepareStatement(query);
                statement.setString(1, donatorNou.getSare());
                statement.setString(2, donatorNou.getPasswordHash());
                statement.setString(3, usernameDoctorVechi);
                statement.execute();
            }

            query = "UPDATE donatori SET nume = ?, prenume = ?, grupa_sanguina = ? WHERE id = ?";
            statement = connection.prepareStatement(query);
            statement.setString(1, donatorNou.getNume());
            statement.setString(2, donatorNou.getPrenume());
            statement.setInt(3, donatorNou.getGrupaSanguina().toInteger());
            statement.setInt(4, donatorVechi.getId());
            statement.execute();

            connection.commit();
        } catch (SQLException e) {
            connection.rollback();
            e.printStackTrace();
        }
        connection.setAutoCommit(true);
    }

    public static void deleteDonator(int donatorId) throws SQLException {
        String query = "DELETE FROM donatori WHERE id = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, donatorId);
        statement.execute();
    }
}
