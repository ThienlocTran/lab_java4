package com.thienloc.springboot.lab.repository;

import com.thienloc.springboot.lab.entity.Video;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VideoRepository extends JpaRepository<Video, Long> {
    
    List<Video> findByTitleContainingIgnoreCase(String keyword);
    
    @Query("SELECT v FROM Video v WHERE SIZE(v.favorites) = 0 ORDER BY v.id")
    List<Video> findVideosWithoutFavorites();
    
    @Query(value = "SELECT TOP 10 v.id, v.title, v.poster, v.views, v.description, v.active " +
                   "FROM Videos v " +
                   "LEFT JOIN Favorite f ON v.id = f.Videold " +
                   "GROUP BY v.id, v.title, v.poster, v.views, v.description, v.active " +
                   "ORDER BY COUNT(f.id) DESC", nativeQuery = true)
    List<Video> findTop10MostFavorited();
}
