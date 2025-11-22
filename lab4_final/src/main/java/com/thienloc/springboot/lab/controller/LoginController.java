package com.thienloc.springboot.lab.controller;

import com.thienloc.springboot.lab.entity.User;
import com.thienloc.springboot.lab.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpSession;
import java.util.Optional;

@Controller
public class LoginController {
    
    @Autowired
    private UserService userService;
    
    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }
    
    @PostMapping("/login")
    public String login(@RequestParam String username, 
                       @RequestParam String password,
                       HttpSession session,
                       RedirectAttributes redirectAttributes,
                       Model model) {
        Optional<User> userOpt = userService.findByIdOrEmail(username);
        
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            if (user.getPassword().equals(password)) {
                session.setAttribute("user", user);
                session.setAttribute("userId", user.getId());
                session.setAttribute("userName", user.getFullname());
                redirectAttributes.addFlashAttribute("message", "Đăng nhập thành công!");
                return "redirect:/";
            } else {
                model.addAttribute("error", "Mật khẩu không chính xác!");
                return "login";
            }
        } else {
            model.addAttribute("error", "Tài khoản không tồn tại!");
            return "login";
        }
    }
    
    @GetMapping("/logout")
    public String logout(HttpSession session, RedirectAttributes redirectAttributes) {
        session.invalidate();
        redirectAttributes.addFlashAttribute("message", "Đã đăng xuất thành công!");
        return "redirect:/login";
    }
}
