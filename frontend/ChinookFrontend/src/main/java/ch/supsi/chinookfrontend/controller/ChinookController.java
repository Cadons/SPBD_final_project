package ch.supsi.chinookfrontend.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ChinookController {
    @GetMapping("/")
    public String index(Model model) {
        return "index";
    }

    @GetMapping("/customers")
    public String customers(Model model) {
        return "customers";
    }

    @GetMapping("/profile")
    public String profile(Model model) {



        return "profile";
    }
    @GetMapping("/changePassword")
    public String changePassword(Model model) {



        return "changePassword";
    }

    @GetMapping("/logout")
    public String logout(Model model) {
        return "index";
    }
}
