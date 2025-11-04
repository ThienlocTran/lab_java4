package com.thienloc.springboot.lab4.repository;

import com.thienloc.springboot.lab4.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    
    /**
     * Tìm User theo email
     * Spring Data JPA tự động generate JPQL: SELECT u FROM User u WHERE u.email LIKE %:email%
     */
    Optional<User> findByEmailContaining(String email);
    
    /**
     * Tìm tất cả User có role admin
     * Spring Data JPA tự động generate JPQL: SELECT u FROM User u WHERE u.admin = :admin
     */
    List<User> findByAdmin(Boolean admin);
    
    /**
     * Tìm User theo email chứa keyword và không phải admin
     * Spring Data JPA tự động generate JPQL: SELECT u FROM User u WHERE u.email LIKE %:email% AND u.admin = false
     */
    List<User> findByEmailContainingAndAdminFalse(String email);
    
    /**
     * Custom JPQL query - Tìm tất cả User
     * JPQL: SELECT u FROM User u
     */
    @Query("SELECT u FROM User u")
    List<User> findAllUsers();
}
