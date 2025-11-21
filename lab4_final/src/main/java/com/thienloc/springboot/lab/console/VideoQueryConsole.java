package com.thienloc.springboot.lab.console;

import com.thienloc.springboot.lab.entity.Share;
import com.thienloc.springboot.lab.entity.User;
import com.thienloc.springboot.lab.entity.Video;
import com.thienloc.springboot.lab.service.ShareService;
import com.thienloc.springboot.lab.service.UserService;
import com.thienloc.springboot.lab.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Optional;

@Component
public class VideoQueryConsole implements CommandLineRunner {
    
    @Autowired
    private VideoService videoService;
    
    @Autowired
    private ShareService shareService;
    
    @Autowired
    private UserService userService;
    
    @Override
    public void run(String... args) throws Exception {
        System.out.println("\n========================================");
        System.out.println("VIDEO QUERY TESTS");
        System.out.println("========================================\n");
        
        testFindUserByEmail();
        testFindUserById();
        testFindVideosByKeyword();
        testFindTop10MostFavorited();
        testFindVideosWithoutFavorites();
        testFindSharedVideosIn2024();
        
        System.out.println("\n========================================\n");
    }
    
    private void testFindUserByEmail() {
        System.out.println("1. TEST: Find User by Email");
        System.out.println("-----------------------------------");
        Optional<User> user = userService.findByEmail("admin@fpt.edu.vn");
        if (user.isPresent()) {
            System.out.println("Found: " + user.get().getFullname() + " (" + user.get().getEmail() + ")");
        } else {
            System.out.println("User not found with email: admin@fpt.edu.vn");
        }
        System.out.println();
    }
    
    private void testFindUserById() {
        System.out.println("2. TEST: Find User by ID");
        System.out.println("-----------------------------------");
        Optional<User> user = userService.findById(1L);
        if (user.isPresent()) {
            System.out.println("Found: " + user.get().getFullname() + " (" + user.get().getEmail() + ")");
        } else {
            System.out.println("User not found with ID: 1");
        }
        System.out.println();
    }
    
    private void testFindVideosByKeyword() {
        System.out.println("3. TEST: Find Videos by Keyword");
        System.out.println("-----------------------------------");
        List<Video> videos = videoService.findByTitleContaining("video");
        System.out.println("Found " + videos.size() + " video(s) containing 'video':");
        for (int i = 0; i < Math.min(5, videos.size()); i++) {
            System.out.println("  - " + videos.get(i).getTitle());
        }
        if (videos.size() > 5) {
            System.out.println("  ... and " + (videos.size() - 5) + " more");
        }
        System.out.println();
    }
    
    private void testFindTop10MostFavorited() {
        System.out.println("4. TEST: Find Top 10 Most Favorited Videos");
        System.out.println("-----------------------------------");
        List<Video> videos = videoService.findTop10MostFavorited();
        System.out.println("Found " + videos.size() + " most favorited video(s):");
        for (int i = 0; i < Math.min(10, videos.size()); i++) {
            System.out.println("  " + (i + 1) + ". " + videos.get(i).getTitle());
        }
        System.out.println();
    }
    
    private void testFindVideosWithoutFavorites() {
        System.out.println("5. TEST: Find Videos without Favorites");
        System.out.println("-----------------------------------");
        List<Video> videos = videoService.findVideosWithoutFavorites();
        System.out.println("Found " + videos.size() + " video(s) without favorites:");
        for (int i = 0; i < Math.min(5, videos.size()); i++) {
            System.out.println("  - " + videos.get(i).getTitle());
        }
        if (videos.size() > 5) {
            System.out.println("  ... and " + (videos.size() - 5) + " more");
        }
        System.out.println();
    }
    
    private void testFindSharedVideosIn2024() {
        System.out.println("6. TEST: Find Videos Shared in 2024");
        System.out.println("-----------------------------------");
        List<Share> shares = shareService.findSharedVideosIn2024();
        System.out.println("Found " + shares.size() + " shared record(s) in 2024:");
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        for (int i = 0; i < Math.min(5, shares.size()); i++) {
            Share share = shares.get(i);
            System.out.println("  - " + share.getVideo().getTitle() + 
                             " shared on " + dateFormat.format(share.getShareDate()));
        }
        if (shares.size() > 5) {
            System.out.println("  ... and " + (shares.size() - 5) + " more");
        }
        System.out.println();
    }
}
