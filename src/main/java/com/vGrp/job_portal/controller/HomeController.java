package com.vGrp.job_portal.controller;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String homePage(Model model, @AuthenticationPrincipal UserDetails user) {
        if (user != null) {
            model.addAttribute("username", user.getUsername());
        }
        return "index";  // Ensure this matches your `index.html` filename
    }
}
