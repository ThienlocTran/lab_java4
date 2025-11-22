package com.thienloc.springboot.lab.repository;

import com.thienloc.springboot.lab.entity.Share;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface ShareRepository extends JpaRepository<Share, Long>, ShareRepositoryCustom {
    
    List<Share> findByVideoIdOrderByShareDateDesc(Long videoId);
    
    default List<Share> findAll() {
        return findAllShares();
    }
}
