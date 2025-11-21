package com.thienloc.springboot.lab.controller;

import com.thienloc.springboot.lab.entity.Share;
import com.thienloc.springboot.lab.entity.User;
import com.thienloc.springboot.lab.entity.Video;
import com.thienloc.springboot.lab.service.ShareService;
import com.thienloc.springboot.lab.service.UserService;
import com.thienloc.springboot.lab.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/query")
public class QueryController {
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private VideoService videoService;
    
    @Autowired
    private ShareService shareService;
    
    @GetMapping("/user/email/{email}")
    public Optional<User> findUserByEmail(@PathVariable String email) {
        return userService.findByEmail(email);
    }
    
    @GetMapping("/user/id/{id}")
    public Optional<User> findUserById(@PathVariable Long id) {
        return userService.findById(id);
    }
    
    @GetMapping("/video/search")
    public List<Video> searchVideosByKeyword(@RequestParam String keyword) {
        return videoService.findByTitleContaining(keyword);
    }
    
    @GetMapping("/video/top10-favorited")
    public List<Video> getTop10FavoritedVideos() {
        return videoService.findTop10MostFavorited();
    }
    
    @GetMapping("/video/no-favorites")
    public List<Video> getVideosWithoutFavorites() {
        return videoService.findVideosWithoutFavorites();
    }
    
    @GetMapping("/share/2024")
    public List<Share> getSharedVideosIn2024() {
        return shareService.findSharedVideosIn2024();
    }
    
    @GetMapping("/share/video/{videoId}")
    public List<Share> getSharesByVideoId(@PathVariable Long videoId) {
        return shareService.findSharesByVideoId(videoId);
    }
}
