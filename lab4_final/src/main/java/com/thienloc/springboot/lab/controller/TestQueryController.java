package com.thienloc.springboot.lab.controller;

import com.thienloc.springboot.lab.entity.Share;
import com.thienloc.springboot.lab.entity.Video;
import com.thienloc.springboot.lab.service.ShareService;
import com.thienloc.springboot.lab.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/test-query")
public class TestQueryController {
    
    @Autowired
    private VideoService videoService;
    
    @Autowired
    private ShareService shareService;
    
    @GetMapping
    public String testQueryPage(Model model) {
        return "test-query";
    }
    
    @GetMapping("/videos-by-keyword")
    public String searchVideos(@RequestParam(required = false) String keyword, Model model) {
        if (keyword != null && !keyword.isEmpty()) {
            List<Video> videos = videoService.findByTitleContaining(keyword);
            model.addAttribute("videos", videos);
            model.addAttribute("keyword", keyword);
        }
        return "test-query";
    }
    
    @GetMapping("/top10-favorited")
    public String getTop10Favorited(Model model) {
        List<Video> videos = videoService.findTop10MostFavorited();
        model.addAttribute("top10Videos", videos);
        return "test-query";
    }
    
    @GetMapping("/no-favorites")
    public String getNoFavorites(Model model) {
        List<Video> videos = videoService.findVideosWithoutFavorites();
        model.addAttribute("noFavVideos", videos);
        return "test-query";
    }
    
    @GetMapping("/shared-2024")
    public String getShared2024(Model model) {
        List<Share> shares = shareService.findSharedVideosIn2024();
        model.addAttribute("shares2024", shares);
        return "test-query";
    }
}
