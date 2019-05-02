package blog.com.blogtech.Database;

import org.springframework.stereotype.Repository;

import java.sql.*;

@Repository
public class DBAccess {

    private Connection connection;

    /**
     * Method for creation of a connection to the database
     */
    public Connection getConnection() {
        try {
            connection = DriverManager.getConnection(
                    "jdbc:mysql://den1.mysql4.gear.host/techblogdb",
                    "techblogdb",
                    "Kh98-4_9BE7l");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }
}
