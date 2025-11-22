package com.thienloc.springboot.lab.service;

import com.thienloc.springboot.lab.entity.Share;
import com.thienloc.springboot.lab.repository.ShareRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ShareService {
    
    @Autowired
    private ShareRepository shareRepository;
    
    public List<Share> findAll() {
        return shareRepository.findAll();
    }
    
    public Optional<Share> findById(Long id) {
        return shareRepository.findById(id);
    }
    
    public Share create(Share share) {
        return shareRepository.save(share);
    }
    
    public Share update(Share share) {
        return shareRepository.save(share);
    }
    
    public void deleteById(Long id) {
        shareRepository.deleteById(id);
    }
    
    public List<Share> findSharedVideosIn2024() {
        try {
            List<Share> allShares = shareRepository.findAllShares();
            System.out.println("DEBUG: Total shares fetched: " + allShares.size());
            
            List<Share> shares2024 = new java.util.ArrayList<>();
            for (Share share : allShares) {
                System.out.println("DEBUG: Share ID=" + share.getId() + ", shareDate=" + share.getShareDate());
                if (share.getShareDate() != null) {
                    java.util.Calendar cal = java.util.Calendar.getInstance();
                    cal.setTime(share.getShareDate());
                    int year = cal.get(java.util.Calendar.YEAR);
                    System.out.println("DEBUG: Year extracted: " + year);
                    if (year == 2024) {
                        shares2024.add(share);
                    }
                }
            }
            System.out.println("DEBUG: Shares in 2024: " + shares2024.size());
            
            // Sort descending by shareDate
            shares2024.sort((a, b) -> {
                if (a.getShareDate() == null || b.getShareDate() == null) return 0;
                return b.getShareDate().compareTo(a.getShareDate());
            });
            return shares2024;
        } catch (Exception e) {
            System.out.println("DEBUG: Exception in findSharedVideosIn2024: " + e.getMessage());
            e.printStackTrace();
            return new java.util.ArrayList<>();
        }
    }
    
    public List<Share> findSharesByVideoId(Long videoId) {
        return shareRepository.findByVideoIdOrderByShareDateDesc(videoId);
    }
}
