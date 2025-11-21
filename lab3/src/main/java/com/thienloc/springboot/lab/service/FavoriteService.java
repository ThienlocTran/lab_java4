package com.thienloc.springboot.lab.service;

import com.thienloc.springboot.lab.entity.Favorite;
import com.thienloc.springboot.lab.repository.FavoriteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class FavoriteService {
    
    @Autowired
    private FavoriteRepository favoriteRepository;
    
    public List<Favorite> findAll() {
        return favoriteRepository.findAll();
    }
    
    public Optional<Favorite> findById(Long id) {
        return favoriteRepository.findById(id);
    }
    
    public Favorite create(Favorite favorite) {
        return favoriteRepository.save(favorite);
    }
    
    public Favorite update(Favorite favorite) {
        return favoriteRepository.save(favorite);
    }
    
    public void deleteById(Long id) {
        favoriteRepository.deleteById(id);
    }
}
