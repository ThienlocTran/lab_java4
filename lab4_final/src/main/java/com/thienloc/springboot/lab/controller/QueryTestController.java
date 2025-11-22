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

import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("/query-test")
public class QueryTestController {
    
    @Autowired
    private VideoService videoService;
    
    @Autowired
    private ShareService shareService;
    
    @GetMapping
    public String showQueryPage(Model model) {
        try {
            // Load tất cả dữ liệu mặc định
            model.addAttribute("top10Videos", videoService.findTop10MostFavorited());
            model.addAttribute("noFavVideos", videoService.findVideosWithoutFavorites());
            model.addAttribute("shares2024", shareService.findSharedVideosIn2024());
        } catch (Exception e) {
            model.addAttribute("top10Videos", new ArrayList<>());
            model.addAttribute("noFavVideos", new ArrayList<>());
            model.addAttribute("shares2024", new ArrayList<>());
        }
        
        // Load share summary
        List<Video> videos = videoService.findAll();
        List<Map<String, Object>> summaries = new ArrayList<>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        
        for (Video video : videos) {
            List<Share> shares = shareService.findSharesByVideoId(video.getId());
            if (!shares.isEmpty()) {
                Map<String, Object> summary = new HashMap<>();
                summary.put("videoId", video.getId());
                summary.put("videoTitle", video.getTitle());
                summary.put("videoPoster", video.getPoster());
                summary.put("shareCount", shares.size());
                summary.put("firstShareDate", dateFormat.format(shares.get(shares.size() - 1).getShareDate()));
                summary.put("lastShareDate", dateFormat.format(shares.get(0).getShareDate()));
                summary.put("sharedEmails", shares.stream()
                    .map(Share::getEmails)
                    .distinct()
                    .reduce((a, b) -> a + "; " + b)
                    .orElse("N/A"));
                summaries.add(summary);
            }
        }
        model.addAttribute("summaries", summaries);
        
        return "query-test";
    }
    
    @GetMapping("/search-video")
    public String searchVideo(@RequestParam String keyword, Model model) {
        List<Video> videos = videoService.findByTitleContaining(keyword);
        model.addAttribute("videos", videos);
        model.addAttribute("keyword", keyword);
        return "query-test";
    }
    
    @GetMapping("/top10-favorited")
    public String top10Favorited(Model model) {
        List<Video> videos = videoService.findTop10MostFavorited();
        model.addAttribute("top10Videos", videos);
        return "query-test";
    }
    
    @GetMapping("/no-favorites")
    public String noFavorites(Model model) {
        List<Video> videos = videoService.findVideosWithoutFavorites();
        model.addAttribute("noFavVideos", videos);
        return "query-test";
    }
    
    @GetMapping("/shared-2024")
    public String shared2024(Model model) {
        List<Share> allShares = shareService.findAll();
        System.out.println("DEBUG CONTROLLER: Total shares in DB: " + allShares.size());
        for (Share s : allShares) {
            System.out.println("DEBUG CONTROLLER: Share ID=" + s.getId() + ", Date=" + s.getShareDate());
        }
        
        List<Share> shares = shareService.findSharedVideosIn2024();
        System.out.println("DEBUG CONTROLLER: Shares 2024 returned: " + shares.size());
        model.addAttribute("shares2024", shares);
        return "query-test";
    }
    
    @GetMapping("/share-summary")
    public String shareSummary(Model model) {
        List<Video> videos = videoService.findAll();
        List<Map<String, Object>> summaries = new ArrayList<>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        
        for (Video video : videos) {
            List<Share> shares = shareService.findSharesByVideoId(video.getId());
            
            if (!shares.isEmpty()) {
                Map<String, Object> summary = new HashMap<>();
                summary.put("videoId", video.getId());
                summary.put("videoTitle", video.getTitle());
                summary.put("videoPoster", video.getPoster());
                summary.put("shareCount", shares.size());
                summary.put("firstShareDate", dateFormat.format(shares.get(shares.size() - 1).getShareDate()));
                summary.put("lastShareDate", dateFormat.format(shares.get(0).getShareDate()));
                summary.put("sharedEmails", shares.stream()
                    .map(Share::getEmails)
                    .distinct()
                    .reduce((a, b) -> a + "; " + b)
                    .orElse("N/A"));
                
                summaries.add(summary);
            }
        }
        
        model.addAttribute("summaries", summaries);
        return "query-test";
    }
}
