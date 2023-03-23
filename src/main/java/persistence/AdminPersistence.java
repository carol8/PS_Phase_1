package persistence;

import connection.ConnectionSingleton;
import model.Admin;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AdminPersistence {
    private static final Connection connection = ConnectionSingleton.instance().getConnection();

    public static void insertAdmin(Admin admin) throws SQLException {
        String query = "INSERT INTO utilizatori VALUES (?, ?, ?, ?, null, null)";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, admin.getUsername());
        statement.setString(2, admin.getSare());
        statement.setString(3, admin.getPasswordHash());
        statement.setBoolean(4, true);
        statement.execute();
    }

    public static List<Admin> readAdmins(String conditions) throws SQLException {
        List<Admin> result = new ArrayList<>();
        String query;
        if (conditions.length() == 0) {
            query = "SELECT username, sare, hash_parola " +
                    "FROM utilizatori " +
                    "WHERE is_admin = true";
        } else {
            query = "SELECT username, sare, hash_parola " +
                    "FROM utilizatori " +
                    "WHERE is_admin = true and " + conditions;
        }
        PreparedStatement statement = connection.prepareStatement(query);
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            result.add(new Admin(resultSet.getString("username"),
                    resultSet.getString("sare"),
                    resultSet.getString("hash_parola")));
        }
        return result;
    }

    public static void deleteAdmin(String adminUsername) throws SQLException {
        String query = "DELETE FROM utilizatori WHERE is_admin = true and username = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, adminUsername);
        statement.execute();
    }
}
