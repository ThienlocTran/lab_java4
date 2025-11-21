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
    
    /**
     * Tìm tất cả Share của một User
     * Spring Data JPA JPQL: SELECT s FROM Share s WHERE s.user.id = :userId
     */
    public List<Share> findByUserId(Long userId) {
        return shareRepository.findByUserId(userId);
    }
    
    /**
     * Tìm tất cả Share của một Video
     * Spring Data JPA JPQL: SELECT s FROM Share s WHERE s.video.id = :videoId
     */
    public List<Share> findByVideoId(Long videoId) {
        return shareRepository.findByVideoId(videoId);
    }
    
    /**
     * Tìm Share theo User và Video
     * Spring Data JPA JPQL: SELECT s FROM Share s WHERE s.user = :user AND s.video = :video
     */
    public List<Share> findByUserAndVideo(com.thienloc.springboot.lab.entity.User user, com.thienloc.springboot.lab.entity.Video video) {
        return shareRepository.findByUserAndVideo(user, video);
    }
    
    /**
     * Tìm tất cả Share
     * Custom JPQL: SELECT s FROM Share s
     */
    public List<Share> findAllShares() {
        return shareRepository.findAllShares();
    }
}
