package com.thienloc.springboot.lab3.repository;

import com.thienloc.springboot.lab3.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Query("SELECT u FROM User u WHERE u.email LIKE '%fpt%' AND u.admin = false")
    List<User> findFptEmailNonAdminUsers();
}
