package com.thienloc.springboot.lab4.controller;

import com.thienloc.springboot.lab4.entity.Video;
import com.thienloc.springboot.lab4.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
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
}
