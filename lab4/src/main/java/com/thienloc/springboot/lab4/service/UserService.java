package com.thienloc.springboot.lab4.service;

import com.thienloc.springboot.lab4.entity.User;
import com.thienloc.springboot.lab4.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    
    @Autowired
    private UserRepository userRepository;
    
    public List<User> findAll() {
        return userRepository.findAll();
    }
    
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }
    
    public User create(User user) {
        return userRepository.save(user);
    }
    
    public User update(User user) {
        return userRepository.save(user);
    }
    
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }
    
    /**
     * Tìm User có email chứa "fpt" và không phải admin
     * Spring Data JPA JPQL: SELECT u FROM User u WHERE u.email LIKE %fpt% AND u.admin = false
     */
    public List<User> findFptEmailNonAdminUsers() {
        return userRepository.findByEmailContainingAndAdminFalse("fpt");
    }
    
    /**
     * Tìm User theo email
     * Spring Data JPA JPQL: SELECT u FROM User u WHERE u.email LIKE %:email%
     */
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmailContaining(email);
    }
    
    /**
     * Tìm tất cả User có role admin
     * Spring Data JPA JPQL: SELECT u FROM User u WHERE u.admin = true
     */
    public List<User> findAdminUsers() {
        return userRepository.findByAdmin(true);
    }
    
    /**
     * Tìm tất cả User
     * Custom JPQL: SELECT u FROM User u
     */
    public List<User> findAllUsers() {
        return userRepository.findAllUsers();
    }
    
    public List<User> findUsersByPage(int pageNumber, int pageSize) {
        int skip = pageNumber * pageSize;
        List<User> allUsers = userRepository.findAll();
        int endIndex = Math.min(skip + pageSize, allUsers.size());
        if (skip >= allUsers.size()) {
            return List.of();
        }
        return allUsers.subList(skip, endIndex);
    }
    
    public long getTotalUserCount() {
        return userRepository.count();
    }
}
