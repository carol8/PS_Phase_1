package persistence;

import connection.ConnectionSingleton;
import model.Programare;

import java.sql.*;

public class ProgramarePersistence {
    private static final Connection connection = ConnectionSingleton.instance().getConnection();

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
