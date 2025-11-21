package com.thienloc.springboot.lab.service;

import com.thienloc.springboot.lab.entity.Video;
import com.thienloc.springboot.lab.repository.VideoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class VideoService {
    
    @Autowired
    private VideoRepository videoRepository;
    
    public List<Video> findAll() {
        return videoRepository.findAll();
    }
    
    public Optional<Video> findById(Long id) {
        return videoRepository.findById(id);
    }
    
    public Video create(Video video) {
        return videoRepository.save(video);
    }
    
    public Video update(Video video) {
        return videoRepository.save(video);
    }
    
    public void deleteById(Long id) {
        videoRepository.deleteById(id);
    }
    
    /**
     * Tìm Video theo title chứa keyword
     * Spring Data JPA JPQL: SELECT v FROM Video v WHERE v.title LIKE %:title%
     */
    public List<Video> findByTitleContaining(String keyword) {
        return videoRepository.findByTitleContaining(keyword);
    }
    
    /**
     * Tìm tất cả Video không bị xóa (active = true)
     * Spring Data JPA JPQL: SELECT v FROM Video v WHERE v.active = true
     */
    public List<Video> findAllActive() {
        return videoRepository.findByActiveTrue();
    }
    
    /**
     * Tìm Video sắp xếp theo lượt xem giảm dần
     * Spring Data JPA JPQL: SELECT v FROM Video v ORDER BY v.views DESC
     */
    public List<Video> findVideosSortedByViews() {
        return videoRepository.findAll(Sort.by(Sort.Direction.DESC, "views"));
    }
    
    /**
     * Tìm Video có lượt xem lớn hơn giá trị cho trước
     * Spring Data JPA JPQL: SELECT v FROM Video v WHERE v.views > :views
     */
    public List<Video> findVideosByMinViews(Integer minViews) {
        return videoRepository.findByViewsGreaterThan(minViews);
    }
    
    /**
     * Tìm tất cả Video
     * Custom JPQL: SELECT v FROM Video v
     */
    public List<Video> findAllVideos() {
        return videoRepository.findAllVideos();
    }
    
    /**
     * Truy vấn 10 video được yêu thích nhiều nhất
     * JPQL: SELECT v FROM Video v LEFT JOIN v.favorites f GROUP BY v.id ORDER BY COUNT(f.id) DESC
     */
    public List<Video> findTop10MostLikedVideos() {
        try {
            List<Video> result = videoRepository.findTop10MostLikedVideos(10);
            return result != null ? result : new java.util.ArrayList<>();
        } catch (Exception e) {
            System.err.println("Error in findTop10MostLikedVideos: " + e.getMessage());
            e.printStackTrace();
            return new java.util.ArrayList<>();
        }
    }
    
    /**
     * Tìm các video không được ai thích
     * JPQL: SELECT v FROM Video v LEFT JOIN v.favorites f GROUP BY v.id HAVING COUNT(f.id) = 0
     */
    public List<Video> findVideosNotLikedByAnyone() {
        try {
            List<Video> result = videoRepository.findVideosNotLikedByAnyone();
            return result != null ? result : new java.util.ArrayList<>();
        } catch (Exception e) {
            System.err.println("Error in findVideosNotLikedByAnyone: " + e.getMessage());
            e.printStackTrace();
            return new java.util.ArrayList<>();
        }
    }
    
    /**
     * Tìm video được chia sẻ trong năm 2024 và sắp xếp theo thời gian
     * JPQL: SELECT DISTINCT v FROM Video v INNER JOIN v.shares s WHERE s.shareDate >= '2024-01-01' AND s.shareDate <= '2024-12-31'
     */
    public List<Video> findVideosSharedIn2024() {
        try {
            List<Video> result = videoRepository.findVideosSharedIn2024();
            return result != null ? result : new java.util.ArrayList<>();
        } catch (Exception e) {
            System.err.println("Error in findVideosSharedIn2024: " + e.getMessage());
            e.printStackTrace();
            return new java.util.ArrayList<>();
        }
    }
}
