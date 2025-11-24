package com.thienloc.springboot.lab.controller;

import com.thienloc.springboot.lab.entity.Video;
import com.thienloc.springboot.lab.entity.Favorite;
import com.thienloc.springboot.lab.service.VideoService;
import com.thienloc.springboot.lab.service.FavoriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
@RequestMapping("/search")
public class SearchController {
    
    @Autowired
    private VideoService videoService;
    
    @Autowired
    private FavoriteService favoriteService;
    
    @GetMapping
    public String searchPage(@RequestParam(required = false) String keyword, Model model) {
        if (keyword != null && !keyword.isEmpty()) {
            List<Video> videos = videoService.findByTitleContaining(keyword);
            
            // Tính số lượt thích cho mỗi video
            List<Map<String, Object>> searchResults = new java.util.ArrayList<>();
            for (Video video : videos) {
                Map<String, Object> result = new HashMap<>();
                result.put("id", video.getId());
                result.put("title", video.getTitle());
                result.put("views", video.getViews());
                result.put("active", video.getActive());
                result.put("description", video.getDescription());
                
                Optional<Video> favorites = favoriteService.findByVideoId(video.getId());
                result.put("favoriteCount", favorites.stream().count());
                
                searchResults.add(result);
            }
            
            model.addAttribute("searchResults", searchResults);
            model.addAttribute("keyword", keyword);
            model.addAttribute("resultCount", searchResults.size());
        }
        return "search";
    }
}
