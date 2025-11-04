package com.thienloc.springboot.lab.controller;

import com.thienloc.springboot.lab.entity.User;
import com.thienloc.springboot.lab.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/user/crud")
public class UserController {
    
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
    
    // REST API Endpoints (kept for backward compatibility)
    @RestController
    @RequestMapping("/api/users")
    public class UserRestController {
        
        @GetMapping
        public ResponseEntity<List<User>> getAllUsers() {
            List<User> users = userService.findAll();
            return ResponseEntity.ok(users);
        }

        @GetMapping("/{id}")
        public ResponseEntity<User> getUserById(@PathVariable Long id) {
            Optional<User> user = userService.findById(id);
            return user.map(ResponseEntity::ok)
                       .orElseGet(() -> ResponseEntity.notFound().build());
        }
        
        @PostMapping
        public ResponseEntity<User> createUser(@RequestBody User user) {
            User createdUser = userService.create(user);
            return ResponseEntity.ok(createdUser);
        }
        
        @PutMapping("/{id}")
        public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User userDetails) {
            Optional<User> user = userService.findById(id);
            if (user.isPresent()) {
                User existingUser = user.get();
                if (userDetails.getPassword() != null) {
                    existingUser.setPassword(userDetails.getPassword());
                }
                if (userDetails.getFullname() != null) {
                    existingUser.setFullname(userDetails.getFullname());
                }
                if (userDetails.getEmail() != null) {
                    existingUser.setEmail(userDetails.getEmail());
                }
                if (userDetails.getAdmin() != null) {
                    existingUser.setAdmin(userDetails.getAdmin());
                }
                User updatedUser = userService.update(existingUser);
                return ResponseEntity.ok(updatedUser);
            }
            return ResponseEntity.notFound().build();
        }
        
        @DeleteMapping("/{id}")
        public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
            Optional<User> user = userService.findById(id);
            if (user.isPresent()) {
                userService.deleteById(id);
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.notFound().build();
        }
        
        @GetMapping("/search/fpt-non-admin")
        public ResponseEntity<List<User>> searchFptEmailNonAdminUsers() {
            List<User> users = userService.findFptEmailNonAdminUsers();
            return ResponseEntity.ok(users);
        }
        
        @GetMapping("/page")
        public ResponseEntity<?> getUsersByPage(
                @RequestParam(value = "pageNumber", defaultValue = "0") int pageNumber,
                @RequestParam(value = "pageSize", defaultValue = "5") int pageSize) {
            
            List<User> users = userService.findUsersByPage(pageNumber, pageSize);
            long totalCount = userService.getTotalUserCount();
            long totalPages = (totalCount + pageSize - 1) / pageSize;
            
            return ResponseEntity.ok(new java.util.HashMap<String, Object>() {{
                put("pageNumber", pageNumber);
                put("pageSize", pageSize);
                put("totalUsers", totalCount);
                put("totalPages", totalPages);
                put("users", users);
            }});
        }
    }
}
