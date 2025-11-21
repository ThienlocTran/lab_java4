package com.thienloc.springboot.lab.repository;

import com.thienloc.springboot.lab.entity.Video;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
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
}
