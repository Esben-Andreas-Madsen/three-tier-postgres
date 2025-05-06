package database.postgres;

import java.sql.Connection;
import java.sql.DriverManager;


public class DatabaseConnection implements DatabaseConnectionInterface {

    public Connection getConnection() {
        Connection connection = null;

        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(
                    "jdbc:postgresql://localhost:5432/threetier_db",
                    "admin",
                    "admin");

        } catch (Exception e) {
            e.printStackTrace();
        }

        return connection;
    }
}

