package com.thienloc.springboot.lab.repository;

import com.thienloc.springboot.lab.entity.Share;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;

public class ShareRepositoryImpl implements ShareRepositoryCustom {
    
    @PersistenceContext
    private EntityManager entityManager;
    
    @Override
    public List<Share> findAllShares() {
        String sql = "SELECT * FROM Share ORDER BY shareDate DESC";
        return entityManager.createNativeQuery(sql, Share.class).getResultList();
    }
}
