package persistence;

import connection.ConnectionSingleton;
import model.Locatie;
import model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserPersistence {
    private static final Connection connection = ConnectionSingleton.instance().getConnection();

    public static List<User> readUseri(String conditions) throws SQLException {
        List<User> result = new ArrayList<>();
        String query;
        if(conditions.length() == 0){
            query = "SELECT username, sare, hash_parola " +
                    "FROM utilizatori ";
        }
        else{
            query = "SELECT username, sare, hash_parola " +
                    "FROM utilizatori " +
                    "WHERE " + conditions;
        }
        PreparedStatement statement = connection.prepareStatement(query);
        ResultSet resultSet = statement.executeQuery();
        while(resultSet.next()){
            result.add(new User(resultSet.getString("username"),
                    resultSet.getString("sare"),
                    resultSet.getString("hash_parola")));
        }
        return result;
    }
}
