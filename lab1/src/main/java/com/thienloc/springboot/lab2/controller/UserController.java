package com.thienloc.springboot.lab2.controller;

import com.thienloc.springboot.lab2.entity.User;
import com.thienloc.springboot.lab2.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {
    
    @Autowired
    private UserService userService;
    

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
    
    // DELETE: Xóa user
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
