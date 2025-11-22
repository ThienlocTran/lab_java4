package com.thienloc.springboot.lab.service;

import com.thienloc.springboot.lab.entity.User;
import com.thienloc.springboot.lab.repository.UserRepository;
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
    
    public List<User> findFptEmailNonAdminUsers() {
        return userRepository.findFptEmailNonAdminUsers();
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
    
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
    
    public Optional<User> findByIdOrEmail(String idOrEmail) {
        return userRepository.findByIdOrEmail(idOrEmail);
    }
}
