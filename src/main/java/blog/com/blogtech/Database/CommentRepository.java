package blog.com.blogtech.Database;

import blog.com.blogtech.Entities.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

@Repository
public class CommentRepository {

    @Autowired
    private DBAccess dbAccess;

    private PreparedStatement preparedStatement;
    private Statement statement;
    private ResultSet resultSet;
    private String query;

    /**
     * Gets comments with several inner joins to get various info from different tables,
     * also filters by a foreign key linked to a post, so to only get the comments for the selected post
     */
    public ResultSet getPostComments(int id) throws SQLException {
        query = "SELECT comments.*, users.username, roles.role_name FROM comments " +
                "INNER JOIN users ON comments.id_user = users.id " +
                "INNER JOIN roles ON users.id_role = roles.id " +
                "WHERE id_post = '" + id + "' " +
                "ORDER BY comments.date";

            statement = dbAccess.getConnection().createStatement();
            resultSet = statement.executeQuery(query);

        return resultSet;
    }

    /**
     * Gets a single comment for editing
     */
    public ResultSet getSingleComment(int id) throws SQLException {
        query = "SELECT * FROM comments " +
                "WHERE id = ?";

            preparedStatement = dbAccess.getConnection().prepareStatement(query);
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();

        return resultSet;
    }

    /**
     * Executes the edit for a comment
     */
    public void editComment(Comment comment) throws SQLException {
        query = "UPDATE comments " +
                "SET content = ? " +
                "WHERE id = ?";

            preparedStatement = dbAccess.getConnection().prepareStatement(query);
            preparedStatement.setString(1, comment.getContent());
            preparedStatement.setInt(2, comment.getId());
            preparedStatement.executeUpdate();

            preparedStatement.close();
    }

    /**
     * Deletes a comment by ID
     */
    public void deleteComment(int id) throws SQLException {
        query = "DELETE FROM comments " +
                "WHERE id = ?";

            preparedStatement = dbAccess.getConnection().prepareStatement(query);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();

            preparedStatement.close();
    }

    /**
     * Creates a new comment using the selected posts ID and the ID for the logged in user
     */
    public void addComment(Comment comment) throws SQLException {
        query = "INSERT INTO comments (content, id_user, id_post) " +
                "VALUES (?, ?, ?)";

            preparedStatement = dbAccess.getConnection().prepareStatement(query);
            preparedStatement.setString(1, comment.getContent());
            preparedStatement.setInt(2, comment.getId_user());
            preparedStatement.setInt(3, comment.getId_post());
            preparedStatement.executeUpdate();

            preparedStatement.close();
    }
}
