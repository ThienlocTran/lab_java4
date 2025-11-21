package com.thienloc.springboot.lab.controller;

import com.thienloc.springboot.lab.entity.Favorite;
import com.thienloc.springboot.lab.entity.User;
import com.thienloc.springboot.lab.service.FavoriteService;
import com.thienloc.springboot.lab.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/favorite")
public class FavoriteController {
    
    @Autowired
    private FavoriteService favoriteService;
    
    @Autowired
    private UserService userService;
    
    // Bài 3: Trang web liệt kê các video yêu thích của một người dùng
    @GetMapping("/user/{userId}")
    public String getUserFavorites(@PathVariable Long userId, Model model) {
        Optional<User> user = userService.findById(userId);
        if (user.isPresent()) {
            List<Favorite> favorites = favoriteService.findAll()
                    .stream()
                    .filter(f -> f.getUser().getId().equals(userId))
                    .collect(Collectors.toList());
            model.addAttribute("user", user.get());
            model.addAttribute("favorites", favorites);
            return "favorite-user-list";
        }
        return "redirect:/";
    }
    
    // Bài 4: Trang web hiển danh sách các video đã được yêu thích có căn trức thông tin
    @GetMapping("/list")
    public String listAllFavorites(Model model) {
        List<Favorite> favorites = favoriteService.findAll();
        model.addAttribute("favorites", favorites);
        return "favorite-list";
    }
}
