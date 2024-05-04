package Controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    public static Connection databaseLink;
    public static Connection getConnection() {
        String databaseName = "SM_PDM";
        String databaseUser = "sa";
        String databasePassword = "1";
        String url = "jdbc:sqlserver://26.246.43.198:1433;databaseName=SM_PDM;encrypt=true;trustServerCertificate=true;";

        try{
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            try{
                databaseLink = DriverManager.getConnection(url, databaseUser, databasePassword);
            } catch (SQLException ex) {
                System.out.println("Failed to create the database connection.");
            }
        } catch (ClassNotFoundException ex) {
            // log an exception. for example:
            System.out.println("Driver not found.");
        }
        return databaseLink;
    }
}
