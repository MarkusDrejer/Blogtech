package blog.com.blogtech.Controllers;

import blog.com.blogtech.Entities.BlogPost;
import blog.com.blogtech.Services.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;

@Controller
public class BlogController {

    @Autowired
    private BlogService blogService;

    /**
     * Shows blog page with all posts on it
     */
    @GetMapping("/blog")
    public String getAllBlogPosts(Model model) {
        try {
            model.addAttribute("blogposts", blogService.getAllBlogPosts());
            return "blog";

        } catch (SQLException e) {
            model.addAttribute("errorMessage", e.getErrorCode());
            return "error";
        }
    }

    /**
     * Sends to page for editing a post with the post selected
     */
    @GetMapping("/blog/editpost/{id}")
    public String editPost(@PathVariable(value = "id") int id, Model model) {
        try {
            model.addAttribute("post", blogService.getSinglePost(id));
            return "editpost";

        } catch (SQLException e) {
            model.addAttribute("errorMessage", e.getErrorCode());
            return "error";
        }
    }

    /**
     * Executes the edit
     */
    @PostMapping("/blog/editpost")
    public String editPost(@ModelAttribute BlogPost post, Model model) {
        try {
            blogService.editPost(post);
            return "redirect:/blog";
        } catch (SQLException e) {
            model.addAttribute("errorMessage", e.getErrorCode());
            return "error";
        }
    }

    /**
     * Deletes a post
     */
    @PostMapping("/blog/deletepost/{id}")
    public String deletePost(@PathVariable(value = "id") int id, Model model) {
        try {
            blogService.deletePost(id);
            return "redirect:/blog";
        } catch (SQLException e) {
            model.addAttribute("errorMessage", e.getErrorCode());
            return "error";
        }
    }

    /**
     * Adds a new post
     */
    @PostMapping("/blog/addpost")
    public String addPost(@ModelAttribute BlogPost post, Model model) {
        try {
            blogService.addPost(post);
            return "redirect:/blog";
        } catch (SQLException e) {
            model.addAttribute("errorMessage", e.getErrorCode());
            return "error";
        }
    }
}
