package com.thienloc.springboot.lab4.service;

import com.thienloc.springboot.lab4.entity.Share;
import com.thienloc.springboot.lab4.repository.ShareRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ShareService {
    
    @Autowired
    private ShareRepository shareRepository;
    
    public List<Share> findAll() {
        return shareRepository.findAll();
    }
    
    public Optional<Share> findById(Long id) {
        return shareRepository.findById(id);
    }
    
    public Share create(Share share) {
        return shareRepository.save(share);
    }
    
    public Share update(Share share) {
        return shareRepository.save(share);
    }
    
    public void deleteById(Long id) {
        shareRepository.deleteById(id);
    }
}
