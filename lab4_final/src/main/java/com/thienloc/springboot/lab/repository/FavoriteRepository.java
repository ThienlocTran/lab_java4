package com.thienloc.springboot.lab.repository;

import com.thienloc.springboot.lab.entity.Favorite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FavoriteRepository extends JpaRepository<Favorite, Long> {
    
    @Query("SELECT COUNT(f) FROM Favorite f WHERE f.video.id = :videoId")
    Long countByVideoId(Long videoId);
    
    List<Favorite> findByUserIdOrderByLikeDateDesc(Long userId);
}
