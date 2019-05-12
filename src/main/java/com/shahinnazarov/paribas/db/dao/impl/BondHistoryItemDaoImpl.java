package com.shahinnazarov.paribas.db.dao.impl;

import com.shahinnazarov.paribas.db.dao.BondHistoryItemDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
public class BondHistoryItemDaoImpl implements BondHistoryItemDao {

    @Autowired
    private EntityManager entityManager;

}
