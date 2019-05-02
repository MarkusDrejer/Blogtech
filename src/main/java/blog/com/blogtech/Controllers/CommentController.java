package blog.com.blogtech.Controllers;

import blog.com.blogtech.Entities.Comment;
import blog.com.blogtech.Services.BlogService;
import blog.com.blogtech.Services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.sql.SQLException;

@Controller
public class CommentController {

    @Autowired
    private CommentService commentService;
    @Autowired
    private BlogService blogService;

    /**
     * Shows a page with a single post and all comments related to it
     */
    @GetMapping("/blog/post/{id}/comments")
    public String getComments(@PathVariable(value = "id") int id, Model model, HttpSession session) {
        try {
            model.addAttribute("post", blogService.getSinglePost(id));
            model.addAttribute("comments", commentService.getPostComments(id));
            session.setAttribute("postid", id);
            return "comments";

        } catch (SQLException e) {
            model.addAttribute("errorMessage", e.getErrorCode());
            return "error";
        }
    }

    /**
     * Allows editing of a comment by a new page
     */
    @GetMapping("/blog/post/editcomment/{id}")
    public String editComment(@PathVariable(value = "id") int id, Model model) {
        try {
            model.addAttribute("comment", commentService.getSingleComment(id));
            return "editcomment";

        } catch (SQLException e) {
            model.addAttribute("errorMessage", e.getErrorCode());
            return "error";
        }
    }

    /**
     * Executes the editing
     */
    @PostMapping("/blog/post/editcomment")
    public String editComment(@ModelAttribute Comment comment, Model model) {
        try {
            commentService.editComment(comment);
            return "redirect:/blog/post/" + comment.getId_post() + "/comments";
        } catch (SQLException e) {
            model.addAttribute("errorMessage", e.getErrorCode());
            return "error";
        }
    }

    /**
     * Executes a deleting of a comment
     */
    @PostMapping("/blog/post/deletecomment/{id}")
    public String deletePost(@PathVariable(value = "id") int id, HttpSession session, Model model) {
        try {
            commentService.deleteComment(id);
            return "redirect:/blog/post/" + session.getAttribute("postid") + "/comments";
        } catch (SQLException e) {
            model.addAttribute("errorMessage", e.getErrorCode());
            return "error";
        }
    }

    /**
     * Creates a new comment on a post
     */
    @PostMapping("/blog/post/addcomment")
    public String addPost(@ModelAttribute Comment comment, Model model) {
        try {
            commentService.addComment(comment);
            return "redirect:/blog/post/" + comment.getId_post() + "/comments";
        } catch (SQLException e) {
            model.addAttribute("errorMessage", e.getErrorCode());
            return "error";
        }
    }
}
