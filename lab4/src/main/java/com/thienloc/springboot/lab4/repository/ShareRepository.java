package com.thienloc.springboot.lab4.repository;

import com.thienloc.springboot.lab4.entity.Share;
import com.thienloc.springboot.lab4.entity.User;
import com.thienloc.springboot.lab4.entity.Video;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShareRepository extends JpaRepository<Share, Long> {
    
    /**
     * Tìm tất cả Share của một User
     * Spring Data JPA tự động generate JPQL: SELECT s FROM Share s WHERE s.user.id = :id
     */
    List<Share> findByUserId(Long userId);
    
    /**
     * Tìm tất cả Share của một Video
     * Spring Data JPA tự động generate JPQL: SELECT s FROM Share s WHERE s.video.id = :id
     */
    List<Share> findByVideoId(Long videoId);
    
    /**
     * Tìm Share theo User và Video
     * Spring Data JPA tự động generate JPQL: SELECT s FROM Share s WHERE s.user = :user AND s.video = :video
     */
    List<Share> findByUserAndVideo(User user, Video video);
    
    /**
     * Custom JPQL query - Tìm tất cả Share
     * JPQL: SELECT s FROM Share s
     */
    @Query("SELECT s FROM Share s")
    List<Share> findAllShares();
}
