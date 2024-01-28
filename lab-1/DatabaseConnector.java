import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnector {
    static final String JDBC_URL = "jdbc:oracle:thin:@localhost:1521/xe";
    static final String USERNAME = "system";
    static final String PASSWORD = "abhi";

    public static Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
            // System.out.println("Connected to the database!");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            System.out.println("Failed to connect to the database!");
        }
        return connection;
}
}