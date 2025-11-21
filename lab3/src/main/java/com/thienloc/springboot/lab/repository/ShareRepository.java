package com.thienloc.springboot.lab.repository;

import com.thienloc.springboot.lab.entity.Share;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShareRepository extends JpaRepository<Share, Long> {
}
