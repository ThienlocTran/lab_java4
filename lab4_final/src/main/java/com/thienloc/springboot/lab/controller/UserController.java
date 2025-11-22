package com.thienloc.springboot.lab.controller;

import com.thienloc.springboot.lab.entity.User;
import com.thienloc.springboot.lab.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/user/crud")
public class    UserController {
    
    @Autowired
    private UserService userService;
    
    // MVC Endpoints
    @GetMapping("/index")
    public String index(Model model) {
        List<User> users = userService.findAll();
        model.addAttribute("users", users);
        model.addAttribute("user", new User());
        return "user-crud";
    }
    
    @PostMapping("/create")
    public String create(@ModelAttribute User user, RedirectAttributes redirectAttributes) {
        try {
            userService.create(user);
            redirectAttributes.addFlashAttribute("message", "Create: " + user.getFullname());
            redirectAttributes.addFlashAttribute("users", userService.findAll());
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", "Error creating user");
        }
        return "redirect:/user/crud/index";
    }
    
    @PostMapping("/edit")
    public String edit(@RequestParam Long id, Model model) {
        Optional<User> user = userService.findById(id);
        if (user.isPresent()) {
            model.addAttribute("user", user.get());
            model.addAttribute("users", userService.findAll());
            return "user-crud";
        }
        return "redirect:/user/crud/index";
    }
    
    @PostMapping("/update")
    public String update(@ModelAttribute User user, RedirectAttributes redirectAttributes) {
        try {
            Optional<User> existingUser = userService.findById(user.getId());
            if (existingUser.isPresent()) {
                User userToUpdate = existingUser.get();
                if (user.getPassword() != null && !user.getPassword().isEmpty()) {
                    userToUpdate.setPassword(user.getPassword());
                }
                if (user.getFullname() != null) {
                    userToUpdate.setFullname(user.getFullname());
                }
                if (user.getEmail() != null) {
                    userToUpdate.setEmail(user.getEmail());
                }
                if (user.getAdmin() != null) {
                    userToUpdate.setAdmin(user.getAdmin());
                }
                userService.update(userToUpdate);
                redirectAttributes.addFlashAttribute("message", "Update: " + user.getId());
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", "Error updating user");
        }
        return "redirect:/user/crud/index";
    }
    
    @PostMapping("/delete")
    public String delete(@RequestParam Long id, RedirectAttributes redirectAttributes) {
        try {
            redirectAttributes.addFlashAttribute("message", "Delete: " + id);
            userService.deleteById(id);
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", "Error deleting user");
        }
        return "redirect:/user/crud/index";
    }
    
    @PostMapping("/reset")
    public String reset(RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("message", "");
        return "redirect:/user/crud/index";
    }
}
