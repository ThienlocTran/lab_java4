package com.thienloc.springboot.lab2.repository;

import com.thienloc.springboot.lab2.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
