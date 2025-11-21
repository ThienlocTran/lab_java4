package com.thienloc.springboot.lab.controller;

import com.thienloc.springboot.lab.entity.Video;
import com.thienloc.springboot.lab.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.http.ResponseEntity;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/video")
public class VideoController {
    
    @Autowired
    private VideoService videoService;
    
    // MVC Endpoints
    @GetMapping("/list")
    public String list(Model model) {
        List<Video> videos = videoService.findAll();
        model.addAttribute("videos", videos);
        return "video-list";
    }
    
    @GetMapping("/crud")
    public String crud(Model model) {
        List<Video> videos = videoService.findAll();
        model.addAttribute("videos", videos);
        model.addAttribute("video", new Video());
        return "video-crud";
    }
    
    @PostMapping("/create")
    public String create(@ModelAttribute Video video, RedirectAttributes redirectAttributes) {
        try {
            videoService.create(video);
            redirectAttributes.addFlashAttribute("message", "Create: " + video.getTitle());
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", "Error creating video");
        }
        return "redirect:/video/crud";
    }
    
    @PostMapping("/edit")
    public String edit(@RequestParam Long id, Model model) {
        Optional<Video> video = videoService.findById(id);
        if (video.isPresent()) {
            model.addAttribute("video", video.get());
            model.addAttribute("videos", videoService.findAll());
            return "video-crud";
        }
        return "redirect:/video/crud";
    }
    
    @PostMapping("/update")
    public String update(@ModelAttribute Video video, RedirectAttributes redirectAttributes) {
        try {
            Optional<Video> existingVideo = videoService.findById(video.getId());
            if (existingVideo.isPresent()) {
                Video videoToUpdate = existingVideo.get();
                if (video.getTitle() != null) {
                    videoToUpdate.setTitle(video.getTitle());
                }
                if (video.getPoster() != null) {
                    videoToUpdate.setPoster(video.getPoster());
                }
                if (video.getViews() != null) {
                    videoToUpdate.setViews(video.getViews());
                }
                if (video.getDescription() != null) {
                    videoToUpdate.setDescription(video.getDescription());
                }
                if (video.getActive() != null) {
                    videoToUpdate.setActive(video.getActive());
                }
                videoService.update(videoToUpdate);
                redirectAttributes.addFlashAttribute("message", "Update: " + video.getId());
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", "Error updating video");
        }
        return "redirect:/video/crud";
    }
    
    @PostMapping("/delete")
    public String delete(@RequestParam Long id, RedirectAttributes redirectAttributes) {
        try {
            redirectAttributes.addFlashAttribute("message", "Delete: " + id);
            videoService.deleteById(id);
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", "Error deleting video");
        }
        return "redirect:/video/crud";
    }
    
    // REST API Endpoints for JPQL Queries
    
    /**
     * Tìm videos theo title chứa keyword
     * JPQL: SELECT v FROM Video v WHERE v.title LIKE %:title%
     */
    @GetMapping("/api/search")
    public ResponseEntity<List<Video>> searchByTitle(@RequestParam String keyword) {
        List<Video> videos = videoService.findByTitleContaining(keyword);
        return ResponseEntity.ok(videos);
    }
    
    /**
     * Truy vấn 10 video được yêu thích nhiều nhất
     * JPQL: SELECT v FROM Video v ORDER BY SIZE(v.favorites) DESC
     */
    @GetMapping("/api/top-liked")
    public ResponseEntity<List<Video>> getTop10MostLikedVideos() {
        List<Video> videos = videoService.findTop10MostLikedVideos();
        return ResponseEntity.ok(videos);
    }
    
    /**
     * Tìm các video không được ai thích
     * JPQL: SELECT v FROM Video v WHERE SIZE(v.favorites) = 0
     */
    @GetMapping("/api/not-liked")
    public ResponseEntity<List<Video>> getVideosNotLikedByAnyone() {
        try {
            List<Video> videos = videoService.findVideosNotLikedByAnyone();
            return ResponseEntity.ok(videos != null ? videos : new java.util.ArrayList<>());
        } catch (Exception e) {
            System.err.println("Error in getVideosNotLikedByAnyone: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.ok(new java.util.ArrayList<>());
        }
    }
    
    /**
     * Tìm video được chia sẻ trong năm 2024 và sắp xếp theo thời gian
     * JPQL: SELECT DISTINCT v FROM Video v JOIN v.shares s WHERE YEAR(s.shareDate) = 2024 ORDER BY s.shareDate DESC
     */
    @GetMapping("/api/shared-2024")
    public ResponseEntity<List<Video>> getVideosSharedIn2024() {
        try {
            List<Video> videos = videoService.findVideosSharedIn2024();
            return ResponseEntity.ok(videos != null ? videos : new java.util.ArrayList<>());
        } catch (Exception e) {
            System.err.println("Error in getVideosSharedIn2024: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.ok(new java.util.ArrayList<>());
        }
    }
}
