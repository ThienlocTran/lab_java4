package com.thienloc.springboot.lab.repository;

import com.thienloc.springboot.lab.entity.Video;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VideoRepository extends JpaRepository<Video, Long> {

    /**
     * Tìm Video theo title chứa keyword
     * Spring Data JPA tự động generate JPQL: SELECT v FROM Video v WHERE v.title LIKE %:title%
     */
    List<Video> findByTitleContaining(String title);

    /**
     * Tìm Video không bị xóa (active = true)
     * Spring Data JPA tự động generate JPQL: SELECT v FROM Video v WHERE v.active = true
     */
    List<Video> findByActiveTrue();

    /**
     * Tìm Video sắp xếp theo lượt xem giảm dần
     * Spring Data JPA tự động generate JPQL: SELECT v FROM Video v ORDER BY v.views DESC
     */
    List<Video> findAll(Sort sort);

    /**
     * Tìm Video có lượt xem lớn hơn giá trị cho trước
     * Spring Data JPA tự động generate JPQL: SELECT v FROM Video v WHERE v.views > :views
     */
    List<Video> findByViewsGreaterThan(Integer views);

    /**
     * Custom JPQL query - Tìm tất cả Video
     * JPQL: SELECT v FROM Video v
     */
    @Query("SELECT v FROM Video v")
    List<Video> findAllVideos();

    /**
     * Truy vấn 10 video được yêu thích nhiều nhất
     * JPQL: SELECT v FROM Video v LEFT JOIN v.favorites f GROUP BY v.id ORDER BY COUNT(f.id) DESC
     * Sử dụng aggregation thay vì SIZE()
     */
    @Query("SELECT v FROM Video v LEFT JOIN v.favorites f GROUP BY v.id, v.title, v.poster, v.views, v.description, v.active ORDER BY COUNT(f.id) DESC")
    List<Video> findTop10MostLikedVideos(@Param("limit") int limit);

    /**
     * Tìm các video không được ai thích
     * JPQL: SELECT v FROM Video v LEFT JOIN v.favorites f GROUP BY v.id HAVING COUNT(f.id) = 0
     */
    @Query("SELECT v FROM Video v LEFT JOIN v.favorites f GROUP BY v.id, v.title, v.poster, v.views, v.description, v.active HAVING COUNT(f.id) = 0")
    List<Video> findVideosNotLikedByAnyone();

    /**
     * Tìm video được chia sẻ trong năm 2024 và sắp xếp theo thời gian
     * JPQL: SELECT DISTINCT v FROM Video v JOIN v.shares s WHERE YEAR(s.shareDate) = 2024 ORDER BY s.shareDate DESC
     */
    @Query("SELECT DISTINCT v FROM Video v INNER JOIN v.shares s WHERE YEAR(s.shareDate) = 2024 ORDER BY s.shareDate DESC")
    List<Video> findVideosSharedIn2024();
}