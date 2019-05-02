package blog.com.blogtech.Services;

import blog.com.blogtech.Database.BlogRepository;
import blog.com.blogtech.Entities.BlogPost;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
public class BlogService {

    @Autowired
    private BlogRepository blogRepository;

    private ResultSet resultSet;

    /**
     * Gets a resultset from the db and creates new blogPost objects with the returned data,
     * also uses a setup method for the data extraction
     */
    public List<BlogPost> getAllBlogPosts() throws SQLException {
        resultSet = blogRepository.getAllBlogPosts();
        List<BlogPost> blogPosts = new ArrayList<>();

        while(resultSet.next()){
            blogPosts.add(blogPostSetup());
        }
        return blogPosts;
    }

    /**
     * As with getAllBlogPosts, but only gets enough data for a single blogPost which gets created via ID
     */
    public BlogPost getSinglePost(int id) throws SQLException{
        resultSet = blogRepository.getSinglePost(id);
        BlogPost post = null;

        while (resultSet.next()) {
            post = blogPostSetup();
        }
        return post;
    }

    /**
     * A Setup method used for ease when creating new objects of blogPost type so to avoid redundancy
     */
    private BlogPost blogPostSetup() throws SQLException {
        BlogPost post = new BlogPost();

        post.setId(resultSet.getInt("id"));
        post.setTitle(resultSet.getString("title"));
        post.setText(resultSet.getString("content"));
        post.setDate(resultSet.getString("date"));
        post.setUsername(resultSet.getString("username"));
        post.setId_user(resultSet.getInt("id_user"));

        return post;
    }

    /**
     * Sends a blogPost object to the repo for editing
     */
    public void editPost(BlogPost post) throws SQLException {
        blogRepository.editPost(post);
    }

    /**
     * Sends a blogPosts ID to the repo for deletion
     */
    public void deletePost(int id) throws SQLException {
        blogRepository.deletePost(id);
    }

    /**
     * Sends a blogPost object to the repo for creation
     */
    public void addPost(BlogPost post) throws SQLException {
        blogRepository.addPost(post);
    }
}
