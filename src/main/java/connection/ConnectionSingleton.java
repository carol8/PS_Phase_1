package connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionSingleton {
    private final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private final String DBURL = "jdbc:mysql://localhost:3306/banca_sanguina";
    private final String USER = "root";
    private final String PASS = "root";
    private final Connection connection;
    private static ConnectionSingleton uniqueInstance;

    private ConnectionSingleton() throws SQLException {
        try {
            Class.forName(DRIVER);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        connection = DriverManager.getConnection(DBURL, USER, PASS);
    }

    public static ConnectionSingleton instance(){
        if(uniqueInstance == null){
            try {
                uniqueInstance = new ConnectionSingleton();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return uniqueInstance;
    }

    public Connection getConnection() {
        return connection;
    }
}
