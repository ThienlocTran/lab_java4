package com.thienloc.springboot.lab.controller;

import com.thienloc.springboot.lab.dto.ShareSummaryDTO;
import com.thienloc.springboot.lab.service.ShareService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/share-summary")
public class ShareSummaryController{
    @Autowired
    private ShareService shareService;
    
    @GetMapping
    public String getShareSummary(Model model) {
        try {
            List<ShareSummaryDTO> shareSummaryList = shareService.getShareSummaryByVideo();
            model.addAttribute("shareSummaryList", shareSummaryList != null ? shareSummaryList : new java.util.ArrayList<>());
            model.addAttribute("message", "");
        } catch (Exception e) {
            System.err.println("Error in getShareSummary: " + e.getMessage());
            e.printStackTrace();
            model.addAttribute("shareSummaryList", new java.util.ArrayList<>());
            model.addAttribute("message", "Error loading share summary: " + e.getMessage());
        }
        return "share-summary";
    }
}
