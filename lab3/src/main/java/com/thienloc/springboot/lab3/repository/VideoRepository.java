package com.thienloc.springboot.lab3.repository;

import com.thienloc.springboot.lab3.entity.Video;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VideoRepository extends JpaRepository<Video, Long> {
}
