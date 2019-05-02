package blog.com.blogtech.Services;

import blog.com.blogtech.Database.LoginRepository;
import blog.com.blogtech.Entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;

@Service
public class LoginService {

    @Autowired
    private LoginRepository loginRepository;

    /**
     * Sends a username and password via a user object to the database,
     * which will return a full user in a resultset if they match any user,
     * then a true will be sent forward along the full user object for a successful login
     */
    public boolean verifyUser(User user) throws SQLException {
        ResultSet resultSet = loginRepository.verifyUser(user);

        if(resultSet.next()) {
            user.setId(resultSet.getInt("id"));
            user.setUsername(resultSet.getString("username"));
            user.setName(resultSet.getString("name"));
            user.setId_role(resultSet.getInt("id_role"));

            return true;
        } else {
            return false;
        }
    }
}
