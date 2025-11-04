package com.thienloc.springboot.lab4.service;

import com.thienloc.springboot.lab4.entity.Video;
import com.thienloc.springboot.lab4.repository.VideoRepository;
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
}
