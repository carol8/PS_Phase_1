package persistence;

import connection.ConnectionSingleton;
import model.Locatie;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LocatiePersistence {
    private static final Connection connection = ConnectionSingleton.instance().getConnection();

    public static List<Locatie> readLocatii(String conditions) throws SQLException {
        List<Locatie> result = new ArrayList<>();
        String query;
        if(conditions.length() == 0){
            query = "SELECT id, nume, adresa, ora_deschidere, ora_inchidere, numar_maxim_recoltari " +
                    "FROM locatii ";
        }
        else{
            query = "SELECT id, nume, adresa, ora_deschidere, ora_inchidere, numar_maxim_recoltari " +
                    "FROM locatii " +
                    "WHERE " + conditions;
        }
        PreparedStatement statement = connection.prepareStatement(query);
        ResultSet resultSet = statement.executeQuery();
        while(resultSet.next()){
            result.add(new Locatie(resultSet.getInt("id"),
                    resultSet.getString("nume"),
                    resultSet.getString("adresa"),
                    resultSet.getTime("ora_deschidere").toLocalTime(),
                    resultSet.getTime("ora_inchidere").toLocalTime(),
                    resultSet.getInt("numar_maxim_recoltari")));
        }
        return result;
    }
}
