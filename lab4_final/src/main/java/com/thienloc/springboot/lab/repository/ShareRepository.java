package com.thienloc.springboot.lab.repository;

import com.thienloc.springboot.lab.entity.Share;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface ShareRepository extends JpaRepository<Share, Long> {
    
    @Query("SELECT s FROM Share s WHERE YEAR(s.shareDate) = 2024 ORDER BY s.shareDate DESC")
    List<Share> findSharedVideosIn2024();
    
    @Query("SELECT s FROM Share s WHERE s.video.id = :videoId ORDER BY s.shareDate DESC")
    List<Share> findSharesByVideoId(Long videoId);
}
