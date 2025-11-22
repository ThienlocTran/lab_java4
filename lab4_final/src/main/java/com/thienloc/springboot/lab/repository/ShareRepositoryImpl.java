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
        String jpql = "SELECT s FROM Share s ORDER BY s.shareDate DESC";
        return entityManager.createQuery(jpql, Share.class).getResultList();
    }
}
