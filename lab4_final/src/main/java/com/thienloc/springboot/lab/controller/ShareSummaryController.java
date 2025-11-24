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
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        
        for (Video video : videos) {
            List<Share> shares = shareService.findSharesByVideoId(video.getId());
            
            if (!shares.isEmpty()) {
                // shares đã được sort descending by shareDate (từ repository)
                // Vậy shares.get(0) = ngày mới nhất, shares.get(last) = ngày cũ nhất
                String lastShareDate = dateFormat.format(shares.get(0).getShareDate());
                String firstShareDate = dateFormat.format(shares.get(shares.size() - 1).getShareDate());
                
                ShareSummaryDto summary = new ShareSummaryDto(
                    video.getId(),
                    video.getTitle(),
                    video.getPoster(),
                    shares.size(),
                    firstShareDate,
                    lastShareDate
                );
                summaries.add(summary);
            }
        }
        
        model.addAttribute("summaries", summaries);
        return "share-summary";
    }
}
