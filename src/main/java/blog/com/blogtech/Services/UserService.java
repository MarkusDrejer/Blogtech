package blog.com.blogtech.Services;

import blog.com.blogtech.Database.UserRepository;
import blog.com.blogtech.Entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    private ResultSet resultSet;

    /**
     * Creates an ArrayList containing all users with information from the database
     */
    public List<User> getAllUsers() throws SQLException {
        resultSet = userRepository.getAllUsers();
        List<User> userList = new ArrayList<>();

        while (resultSet.next()) {
            User user = userSetup();
            user.setRole_name(resultSet.getString("role_name"));
            userList.add(user);
        }
        return userList;
    }

    /**
     * Creates a single user object for editing purposes via ID
     */
    public User getSingleUser(int id) throws SQLException {
        resultSet = userRepository.getSingleUser(id);
        User user = null;

        while (resultSet.next()) {
            user = userSetup();
            user.setPassword(resultSet.getString("password"));
        }
        return user;
    }

    /**
     * A Setup method used for easy setup of a user to remove redundancy
     */
    private User userSetup() throws SQLException {
        User user = new User();

        user.setId(resultSet.getInt("id"));
        user.setUsername(resultSet.getString("username"));
        user.setName(resultSet.getString("name"));
        user.setId_role(resultSet.getInt("id_role"));

        return user;
    }

    /**
     * Sends a user object to the repo for editing
     */
    public void editUser(User user) throws SQLException {
        userRepository.editUser(user);
    }

    /**
     * Sends a user ID to the repo for deletion
     */
    public void deleteUser(int id) throws SQLException {
        userRepository.deleteUser(id);
    }

    /**
     * Sends a user object to the repo for creation
     */
    public void addUser(User user) throws SQLException {
        userRepository.addUser(user);
    }
}
