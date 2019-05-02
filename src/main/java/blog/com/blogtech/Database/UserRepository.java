package blog.com.blogtech.Database;

import blog.com.blogtech.Entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

@Repository
public class UserRepository {

    @Autowired
    private DBAccess dbAccess;

    private PreparedStatement preparedStatement;
    private Statement statement;
    private String query;

    /**
     * Retrieves all users for a full user-list
     */
    public ResultSet getAllUsers() throws  SQLException {
        query = "SELECT users.*, roles.role_name FROM users " +
                "INNER JOIN roles ON users.id_role = roles.id " +
                "ORDER BY id_role";

            statement = dbAccess.getConnection().createStatement();
            return statement.executeQuery(query);
    }

    /**
     * Gets a single user for editing
     */
    public ResultSet getSingleUser(int id) throws SQLException {
        query = "SELECT * FROM users " +
                "WHERE id = ?";

            preparedStatement = dbAccess.getConnection().prepareStatement(query);
            preparedStatement.setInt(1, id);

            return preparedStatement.executeQuery();
    }

    /**
     * Executes edit on a user with new info from the view
     */
    public void editUser(User user) throws SQLException {
        query = "UPDATE users " +
                "SET username = ?, password = ?, name = ?, id_role = ? " +
                "WHERE id = ?";

            preparedStatement = dbAccess.getConnection().prepareStatement(query);
            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setString(3, user.getName());
            preparedStatement.setInt(4, user.getId_role());
            preparedStatement.setInt(5, user.getId());
            preparedStatement.executeUpdate();

            preparedStatement.close();
    }

    /**
     * Deletes a user by ID
     */
    public void deleteUser(int id) throws SQLException {
        query = "DELETE FROM users " +
                "WHERE id = ?";

            preparedStatement = dbAccess.getConnection().prepareStatement(query);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();

            preparedStatement.close();
    }

    /**
     * Creates a new user
     */
    public void addUser(User user) throws SQLException {
        query = "INSERT INTO users (username, password, name, id_role) " +
                "VALUES (?, ?, ?, ?)";

            preparedStatement = dbAccess.getConnection().prepareStatement(query);
            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setString(3, user.getName());
            preparedStatement.setInt(4, user.getId_role());
            preparedStatement.executeUpdate();

            preparedStatement.close();

    }
}
