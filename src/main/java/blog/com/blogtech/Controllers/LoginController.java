package blog.com.blogtech.Controllers;

import blog.com.blogtech.Entities.User;
import blog.com.blogtech.Services.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;
import java.sql.SQLException;

@Controller
public class LoginController {

    @Autowired
    private LoginService loginService;

    /**
     * Start page
     */
    @GetMapping("/")
    public String getHome(){
        return "index";
    }

    /**
     * Creation of a http session which handles login
     */
    @PostMapping("/login")
    public String login(HttpSession httpSession, @ModelAttribute User user, Model model) {
        try {
            if (loginService.verifyUser(user)) {
                httpSession.setAttribute("id", user.getId());
                httpSession.setAttribute("username", user.getUsername());
                httpSession.setAttribute("name", user.getName());
                httpSession.setAttribute("id_role", user.getId_role());
                return "index";

            } else {
                model.addAttribute("invalid", true);
                return "index";
            }

        } catch (SQLException e) {
            model.addAttribute("errorMessage", e.getErrorCode());
            return "error";
        }
    }

    /**
     * Invalidation of the http session which handles logout
     */
    @GetMapping("/logout")
    public String logout(HttpSession httpSession) {
        httpSession.invalidate();
        return "redirect:/";
    }
}
