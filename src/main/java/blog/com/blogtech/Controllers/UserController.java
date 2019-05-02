package blog.com.blogtech.Controllers;

import blog.com.blogtech.Entities.User;
import blog.com.blogtech.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.sql.SQLException;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * User page with all users
     */
    @GetMapping("/users")
    public String userPage(Model model) {
        try {
            model.addAttribute("users", userService.getAllUsers());
            return "users";

        } catch (SQLException e) {
            model.addAttribute("errorMessage", e.getErrorCode());
            return "error";
        }
    }

    /**
     * Page with form for editing a user
     */
    @GetMapping("/users/edituser/{id}")
    public String editUser(@PathVariable(value = "id") int id, Model model) {
        try {
            model.addAttribute("user", userService.getSingleUser(id));
            return "edituser";

        } catch (SQLException e) {
            model.addAttribute("errorMessage", e.getErrorCode());
            return "error";
        }
    }

    /**
     * Post call which will update the user with the new information
     */
    @PostMapping("/users/edituser")
    public String editUser(@ModelAttribute User user, Model model) {
        try {
            userService.editUser(user);
            return "redirect:/users";
        } catch (SQLException e) {
            model.addAttribute("errorMessage", e.getErrorCode());
            return "error";
        }
    }

    /**
     * Deleting of a user
     */
    @PostMapping("/users/deleteuser/{id}")
    public String deleteUser(@PathVariable(value = "id") int id, Model model) {
        try {
            userService.deleteUser(id);
            return "redirect:/users";
        } catch (SQLException e) {
            model.addAttribute("errorMessage", e.getErrorCode());
            return "error";
        }
    }

    /**
     * To page with form for a new user
     */
    @GetMapping("/users/adduser")
    public String addUser() {
        return "adduser";
    }

    /**
     * Post call for the creation of a new user
     */
    @PostMapping("/users/adduser")
    public String addUser(@ModelAttribute User user, Model model) {
        try {
            userService.addUser(user);
            return "redirect:/users";

        } catch (SQLException e) {
            model.addAttribute("errorMessage", e.getErrorCode());
            return "error";
        }
    }
}
