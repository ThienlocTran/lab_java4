package com.thienloc.springboot.lab.controller;

import com.thienloc.springboot.lab.dto.ShareSummaryDto;
import com.thienloc.springboot.lab.entity.Share;
import com.thienloc.springboot.lab.entity.Video;
import com.thienloc.springboot.lab.service.ShareService;
import com.thienloc.springboot.lab.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/share-summary")
public class ShareSummaryController {
    
    @Autowired
    private ShareService shareService;
    
    @Autowired
    private VideoService videoService;
    
    @GetMapping
    public String showShareSummary(Model model) {
        List<Video> videos = videoService.findAll();
        List<ShareSummaryDto> summaries = new ArrayList<>();
        
        for (Video video : videos) {
            List<Share> shares = shareService.findSharesByVideoId(video.getId());
            
            if (!shares.isEmpty()) {
                String emails = shares.stream()
                    .map(Share::getEmails)
                    .distinct()
                    .collect(Collectors.joining(", "));
                
                String lastShareDate = shares.isEmpty() ? "N/A" : 
                    new SimpleDateFormat("dd/MM/yyyy").format(shares.get(0).getShareDate());
                
                ShareSummaryDto summary = new ShareSummaryDto(
                    video.getId(),
                    video.getTitle(),
                    video.getPoster(),
                    shares.size(),
                    emails,
                    lastShareDate
                );
                summaries.add(summary);
            }
        }
        
        model.addAttribute("summaries", summaries);
        return "share-summary";
    }
}
