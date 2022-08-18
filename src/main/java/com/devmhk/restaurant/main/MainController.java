package com.devmhk.restaurant.main;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller
public class MainController {

    @RequestMapping("/")
    public String main(Model model, Authentication authentication) {

        String userId = "";
        try {
            userId = authentication.getName();
        } catch (Exception e) {
        }

        if (userId.equals("")) {
            model.addAttribute("userId", false);
        } else {
            model.addAttribute("userId", true);
        }

        return "main";
    }
}
