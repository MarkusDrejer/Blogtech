package blog.com.blogtech.Database;

import blog.com.blogtech.Entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class LoginRepository {

    @Autowired
    private DBAccess dbAccess;

    private PreparedStatement preparedStatement;
    private ResultSet resultSet;
    private String query;

    /**
     * Gets a user object containing username and password, which will get matched with a user in the database
     */
    public ResultSet verifyUser(User user) throws SQLException {
        query = "SELECT * FROM users " +
                "WHERE username = ? AND password = ?";

            preparedStatement = dbAccess.getConnection().prepareStatement(query);
            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, user.getPassword());
            resultSet = preparedStatement.executeQuery();

        return resultSet;
    }
}
