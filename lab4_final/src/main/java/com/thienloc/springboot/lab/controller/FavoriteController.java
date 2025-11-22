package com.thienloc.springboot.lab.controller;

import com.thienloc.springboot.lab.entity.Favorite;
import com.thienloc.springboot.lab.entity.User;
import com.thienloc.springboot.lab.service.FavoriteService;
import com.thienloc.springboot.lab.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/favorite")
public class FavoriteController {
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private FavoriteService favoriteService;
    
    @GetMapping("/user/{userId}")
    public String userFavorites(@PathVariable Long userId, Model model) {
        Optional<User> user = userService.findById(userId);
        if (user.isPresent()) {
            List<Favorite> favorites = favoriteService.findByUserId(userId);
            model.addAttribute("user", user.get());
            model.addAttribute("favorites", favorites);
            return "favorite-user-list";
        }
        return "redirect:/";
    }
}
