package com.thienloc.springboot.lab.controller;

import com.thienloc.springboot.lab.entity.Share;
import com.thienloc.springboot.lab.entity.Video;
import com.thienloc.springboot.lab.service.ShareService;
import com.thienloc.springboot.lab.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/share")
public class ShareController {
    
    @Autowired
    private ShareService shareService;
    
    @Autowired
    private VideoService videoService;
    
    @GetMapping("/list")
    public String list(Model model) {
        List<Share> shares = shareService.findAll();
        model.addAttribute("shares", shares);
        return "share-list";
    }
    
    @GetMapping("/crud")
    public String crud(Model model) {
        List<Share> shares = shareService.findAll();
        List<Video> videos = videoService.findAll();
        model.addAttribute("shares", shares);
        model.addAttribute("videos", videos);
        model.addAttribute("share", new Share());
        return "share-crud";
    }
    
    @PostMapping("/create")
    public String create(@ModelAttribute Share share, RedirectAttributes redirectAttributes) {
        try {
            shareService.create(share);
            redirectAttributes.addFlashAttribute("message", "Share created successfully");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", "Error creating share: " + e.getMessage());
        }
        return "redirect:/share/crud";
    }
    
    @PostMapping("/delete")
    public String delete(@RequestParam Long id, RedirectAttributes redirectAttributes) {
        try {
            shareService.deleteById(id);
            redirectAttributes.addFlashAttribute("message", "Share deleted successfully");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", "Error deleting share");
        }
        return "redirect:/share/crud";
    }
}
