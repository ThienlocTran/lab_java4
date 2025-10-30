package com.thienloc.springboot.lab3.repository;

import com.thienloc.springboot.lab3.entity.Share;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShareRepository extends JpaRepository<Share, Long> {
}
