package com.thienloc.springboot.lab4.controller;

import com.thienloc.springboot.lab4.entity.Video;
import com.thienloc.springboot.lab4.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HomeController {
    
    @Autowired
    private VideoService videoService;
    
    @GetMapping("/")
    public String index(Model model) {
        // Lấy tất cả video từ database sử dụng JPQL
        List<Video> videos = videoService.findAllVideos();
        
        // Xử lý dữ liệu hiển thị
        for (Video video : videos) {
            video.setThumbnailUrl(video.getPoster() != null ? video.getPoster() : "https://picsum.photos/320/180?random=" + video.getId());
            video.setShortViews(formatNumber(video.getViews()) + " views");
            video.setDuration("10:30");
            video.setTimeAgo("2 days ago");
        }
        
        model.addAttribute("videos", videos);
        return "video-list";
    }
    
    @GetMapping("/home")
    public String home(Model model) {
        return index(model);
    }
    
    private String formatNumber(long number) {
        if (number < 1000) return String.valueOf(number);
        if (number < 1_000_000) return String.format("%.1fK", number / 1000.0);
        return String.format("%.1fM", number / 1_000_000.0);
    }
}
