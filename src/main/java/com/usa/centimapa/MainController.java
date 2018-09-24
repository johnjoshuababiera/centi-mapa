package com.usa.centimapa;

import com.usa.centimapa.user.User;
import com.usa.centimapa.user.UserService;
import com.usa.centimapa.utils.SignInUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class MainController {

    @Autowired
    private UserService userService;

    @RequestMapping("/")
    public String main(Model model, RedirectAttributes redir) {
        try {
            if (userService.noAdminAccount()) {
                userService.initializeAdmin();
            }
            if (SignInUtils.getInstance().getCurrentUser() == null) {
                return "redirect:/login";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        redir.addFlashAttribute("success", model.asMap().get("success"));
        return "redirect:/page/event/";
    }


    @RequestMapping("/login")
    public String loginPage(Model model, RedirectAttributes redir) {
        if (userService.noAdminAccount()) {
            try {
                userService.initializeAdmin();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (SignInUtils.getInstance().getCurrentUser() != null) {
            return "redirect:/page/event/";
        }
        model.addAttribute("user", new User());
        return "/login.html";
    }

    @RequestMapping("/signout")
    public String logout() {
        SignInUtils.getInstance().SignOut();
        return "redirect:/login";
    }


    @PostMapping("/login")
    public String login(@ModelAttribute User user, RedirectAttributes redir) {
        User savedUser = userService.getUser(user.getUsername(), user.getPassword());
        if (savedUser == null) {
            redir.addFlashAttribute("error", "Invalid username/password!");
            return "redirect:/login";
        }
        SignInUtils.getInstance().SignIn(savedUser);
        return "redirect:/";

    }


    @RequestMapping("/register")
    public String register(Model model, RedirectAttributes redir) {
        model.addAttribute("user", new User());
        return "/register.html";
    }


    @PostMapping("/register")
    public String register(@ModelAttribute User user, RedirectAttributes redir) {
        try {
            user.setAdmin(true);
            userService.save(user);
        } catch (Exception e) {
            redir.addFlashAttribute("error", e.getMessage());
            e.printStackTrace();
            return "redirect:/register";
        }
        return "redirect:/";

    }

}
