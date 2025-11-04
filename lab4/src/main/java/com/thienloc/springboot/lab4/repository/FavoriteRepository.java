package com.thienloc.springboot.lab4.repository;

import com.thienloc.springboot.lab4.entity.Favorite;
import com.thienloc.springboot.lab4.entity.User;
import com.thienloc.springboot.lab4.entity.Video;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FavoriteRepository extends JpaRepository<Favorite, Long> {
    
    /**
     * Tìm tất cả Favorite của một User
     * Spring Data JPA tự động generate JPQL: SELECT f FROM Favorite f WHERE f.user.id = :id
     */
    List<Favorite> findByUserId(Long userId);
    
    /**
     * Tìm tất cả Favorite của một Video
     * Spring Data JPA tự động generate JPQL: SELECT f FROM Favorite f WHERE f.video.id = :id
     */
    List<Favorite> findByVideoId(Long videoId);
    
    /**
     * Tìm Favorite theo User và Video
     * Spring Data JPA tự động generate JPQL: SELECT f FROM Favorite f WHERE f.user = :user AND f.video = :video
     */
    List<Favorite> findByUserAndVideo(User user, Video video);
    
    /**
     * Custom JPQL query - Tìm tất cả Favorite
     * JPQL: SELECT f FROM Favorite f
     */
    @Query("SELECT f FROM Favorite f")
    List<Favorite> findAllFavorites();
}
