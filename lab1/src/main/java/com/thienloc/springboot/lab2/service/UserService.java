package com.thienloc.springboot.lab2.service;

import com.thienloc.springboot.lab2.entity.User;
import com.thienloc.springboot.lab2.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private EntityManager entityManager;
    

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
    
    public List<User> findFptEmailNonAdminUsers() {
        String jpql = "SELECT loc FROM User loc WHERE loc.email LIKE :search AND loc.admin = :role";
        TypedQuery<User> query = entityManager.createQuery(jpql, User.class);
        query.setParameter("search", "%@fpt.edu.vn");
        query.setParameter("role", false);
        return query.getResultList();
    }
    
    public List<User> findUsersByPage(int pageNumber, int pageSize) {
        String jpql = "SELECT loc FROM User loc";
        TypedQuery<User> query = entityManager.createQuery(jpql, User.class);
        query.setFirstResult(pageNumber * pageSize);
        query.setMaxResults(pageSize);
        return query.getResultList();
    }
    
    public long getTotalUserCount() {
        String jpql = "SELECT COUNT(loc) FROM User loc";
        return entityManager.createQuery(jpql, Long.class).getSingleResult();
    }
}
