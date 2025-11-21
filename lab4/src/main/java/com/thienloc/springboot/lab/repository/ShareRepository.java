package com.thienloc.springboot.lab.repository;

import com.thienloc.springboot.lab.dto.ShareSummaryDTO;
import com.thienloc.springboot.lab.entity.Share;
import com.thienloc.springboot.lab.entity.User;
import com.thienloc.springboot.lab.entity.Video;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface ShareRepository extends JpaRepository<Share, Long> {
    
    /**
     * Tìm tất cả Share của một User
     */
    List<Share> findByUserId(Long userId);
    
    /**
     * Tìm tất cả Share của một Video
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
    
    /**
     * Lấy thông tin tổng hợp chia sẻ cho mỗi video
     * JPQL: Truy vấn tổng hợp gồm: title, số lượt chia sẻ, ngày chia sẻ đầu tiên, ngày chia sẻ cuối cùng
     * SELECT v.title, COUNT(s.id) as shareCount, MIN(s.shareDate) as firstShareDate, MAX(s.shareDate) as lastShareDate
     * FROM Video v LEFT JOIN v.shares s GROUP BY v.id, v.title ORDER BY v.title
     */
    @Query("SELECT new map(v.id as videoId, v.title as title, COUNT(s.id) as shareCount, MIN(s.shareDate) as firstShareDate, MAX(s.shareDate) as lastShareDate) " +
           "FROM Video v LEFT JOIN v.shares s GROUP BY v.id, v.title ORDER BY v.title")
    List<Map<String, Object>> findShareSummaryByVideo();
    
    @Query("SELECT new com.thienloc.springboot.lab.dto.ShareSummaryDTO(v.title, COUNT(s.id), MIN(s.shareDate), MAX(s.shareDate)) " +
           "FROM Video v LEFT JOIN v.shares s GROUP BY v.id, v.title ORDER BY v.title")
    List<ShareSummaryDTO> getShareSummaryByVideo();
}
