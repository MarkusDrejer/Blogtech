package blog.com.blogtech.Database;

import blog.com.blogtech.Entities.BlogPost;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

@Repository
public class BlogRepository {

    @Autowired
    private DBAccess dbAccess;

    private PreparedStatement preparedStatement;
    private Statement statement;
    private ResultSet resultSet;
    private String query;

    /**
     * Retrieves all blogPosts for use in the complete blog,
     * also gets the users who wrote the blogPosts and sorts by date
     */
    public ResultSet getAllBlogPosts() throws SQLException {
        query = "SELECT posts.*, users.username FROM posts " +
                "INNER JOIN users ON posts.id_user = users.id " +
                "ORDER BY date DESC ";

            statement = dbAccess.getConnection().createStatement();
            resultSet = statement.executeQuery(query);

        return resultSet;
    }

    /**
     * Retrieves a single blogPost by its ID for use in editing to get the form information
     */
    public ResultSet getSinglePost(int id) throws SQLException {
        query = "SELECT posts.*, users.username FROM posts " +
                "INNER JOIN users ON posts.id_user = users.id " +
                "WHERE posts.id = ?";

            preparedStatement = dbAccess.getConnection().prepareStatement(query);
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();

        return resultSet;
    }

    /**
     * For the editing of a blogPost
     * Takes a blogPost object coming from the view and retrieves the info using prepared statements
     */
    public void editPost(BlogPost post) throws SQLException {
        query = "UPDATE posts " +
                "SET title = ?, content = ? " +
                "WHERE id = ?";

            preparedStatement = dbAccess.getConnection().prepareStatement(query);
            preparedStatement.setString(1, post.getTitle());
            preparedStatement.setString(2, post.getText());
            preparedStatement.setInt(3, post.getId());
            preparedStatement.executeUpdate();

            preparedStatement.close();
        }

    /**
     * Uses an ID retrieved from the selected blogPost in view to delete the desired blogPost
     */
    public void deletePost(int id) throws SQLException {
        query = "DELETE FROM posts " +
                "WHERE id = ?";

            preparedStatement = dbAccess.getConnection().prepareStatement(query);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();

            preparedStatement.close();
    }

    /**
     * Creates a new blogPost with information coming from the user via a blogPost object
     */
    public void addPost(BlogPost post) throws SQLException {
        query = "INSERT INTO posts (title, content, id_user) " +
                "VALUES (?, ?, ?)";

             preparedStatement = dbAccess.getConnection().prepareStatement(query);
             preparedStatement.setString(1, post.getTitle());
             preparedStatement.setString(2, post.getText());
             preparedStatement.setInt(3, post.getId_user());
             preparedStatement.executeUpdate();

            preparedStatement.close();
    }
}
