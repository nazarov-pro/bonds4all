package com.shahinnazarov.paribas.db.dao.impl;

import com.shahinnazarov.paribas.db.dao.BondHistoryDao;
import com.shahinnazarov.paribas.db.entity.BondHistory;
import com.shahinnazarov.paribas.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.Collection;

@Repository
public class BondHistoryDaoImpl implements BondHistoryDao {
    @Autowired
    private EntityManager entityManager;

    @Override
    public void save(BondHistory bondHistory) {
        entityManager.persist(bondHistory);
    }

    @Override
    public Collection<BondHistory> listHistoriesByBondReference(String reference) {
        return entityManager
                .createQuery(Constants.HISTORY_LIST_BY_BOND_REFERENCE)
                .setParameter("refKey", reference)
                .getResultList();
    }
}
