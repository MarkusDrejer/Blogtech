package blog.com.blogtech.Services;

import blog.com.blogtech.Database.CommentRepository;
import blog.com.blogtech.Entities.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    private ResultSet resultSet;

    /**
     * Sets up an ArrayList of comments which will be shown on a selected blogPost in view,
     * since the database filters by the blogPost ID only the comments linked to it will be shown
     */
    public List<Comment> getPostComments(int id) throws SQLException {
        resultSet = commentRepository.getPostComments(id);
        List<Comment> comments = new ArrayList<>();

        while(resultSet.next()){
            Comment comment = commentSetup();
            comment.setDate(resultSet.getString("date"));
            comment.setUserName(resultSet.getString("username"));
            comment.setRole(resultSet.getString("role_name"));
            comments.add(comment);
        }
        return comments;
    }

    /**
     * Gets back a single comment to use in editing
     */
    public Comment getSingleComment(int id) throws SQLException{
        resultSet = commentRepository.getSingleComment(id);
        Comment comment = null;

        while (resultSet.next()) {
            comment = commentSetup();
        }
        return comment;
    }

    /**
     * Method used for setting up basic data in a comment object,
     * useful for removing redundancy
     */
    private Comment commentSetup() throws SQLException {
        Comment comment = new Comment();

        comment.setId(resultSet.getInt("id"));
        comment.setContent(resultSet.getString("content"));
        comment.setId_user(resultSet.getInt("id_user"));
        comment.setId_post(resultSet.getInt("id_post"));

        return comment;
    }

    /**
     * Sends a comment object to the repo for editing
     */
    public void editComment(Comment comment) throws SQLException {
        commentRepository.editComment(comment);
    }

    /**
     * Sends a user ID to the repo for deletion
     */
    public void deleteComment(int id) throws SQLException {
        commentRepository.deleteComment(id);
    }

    /**
     * Sends a comment object to the repo for creation
     */
    public void addComment(Comment comment) throws SQLException {
        commentRepository.addComment(comment);
    }
}
