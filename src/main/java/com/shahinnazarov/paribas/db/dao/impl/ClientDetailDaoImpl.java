package com.shahinnazarov.paribas.db.dao.impl;

import com.shahinnazarov.paribas.db.dao.ClientDetailDao;
import com.shahinnazarov.paribas.db.entity.ClientDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
public class ClientDetailDaoImpl implements ClientDetailDao {
    @Autowired
    private EntityManager entityManager;

    @Override
    public void save(ClientDetail clientDetail) {
        entityManager.persist(clientDetail);
    }
}
