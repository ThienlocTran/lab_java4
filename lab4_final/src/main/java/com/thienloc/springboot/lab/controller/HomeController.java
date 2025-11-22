package com.thienloc.springboot.lab.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import jakarta.servlet.http.HttpSession;

@Controller
public class HomeController {
    
    @GetMapping("/")
    public String index(HttpSession session, Model model) {
        Object userId = session.getAttribute("userId");
        Object userName = session.getAttribute("userName");
        
        if (userId != null) {
            model.addAttribute("isLoggedIn", true);
            model.addAttribute("userName", userName);
        }
        
        return "index";
    }
    
    @GetMapping("/home")
    public String home(HttpSession session, Model model) {
        Object userId = session.getAttribute("userId");
        Object userName = session.getAttribute("userName");
        
        if (userId != null) {
            model.addAttribute("isLoggedIn", true);
            model.addAttribute("userName", userName);
        }
        
        return "index";
    }
}
