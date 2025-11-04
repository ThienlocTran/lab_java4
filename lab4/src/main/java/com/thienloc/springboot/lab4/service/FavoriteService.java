package com.thienloc.springboot.lab4.service;

import com.thienloc.springboot.lab4.entity.Favorite;
import com.thienloc.springboot.lab4.repository.FavoriteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class FavoriteService {
    
    @Autowired
    private FavoriteRepository favoriteRepository;
    
    public List<Favorite> findAll() {
        return favoriteRepository.findAll();
    }
    
    public Optional<Favorite> findById(Long id) {
        return favoriteRepository.findById(id);
    }
    
    public Favorite create(Favorite favorite) {
        return favoriteRepository.save(favorite);
    }
    
    public Favorite update(Favorite favorite) {
        return favoriteRepository.save(favorite);
    }
    
    public void deleteById(Long id) {
        favoriteRepository.deleteById(id);
    }
    
    /**
     * Tìm tất cả Favorite của một User
     * Spring Data JPA JPQL: SELECT f FROM Favorite f WHERE f.user.id = :userId
     */
    public List<Favorite> findByUserId(Long userId) {
        return favoriteRepository.findByUserId(userId);
    }
    
    /**
     * Tìm tất cả Favorite của một Video
     * Spring Data JPA JPQL: SELECT f FROM Favorite f WHERE f.video.id = :videoId
     */
    public List<Favorite> findByVideoId(Long videoId) {
        return favoriteRepository.findByVideoId(videoId);
    }
    
    /**
     * Tìm Favorite theo User và Video
     * Spring Data JPA JPQL: SELECT f FROM Favorite f WHERE f.user = :user AND f.video = :video
     */
    public List<Favorite> findByUserAndVideo(com.thienloc.springboot.lab4.entity.User user, com.thienloc.springboot.lab4.entity.Video video) {
        return favoriteRepository.findByUserAndVideo(user, video);
    }
    
    /**
     * Tìm tất cả Favorite
     * Custom JPQL: SELECT f FROM Favorite f
     */
    public List<Favorite> findAllFavorites() {
        return favoriteRepository.findAllFavorites();
    }
}
