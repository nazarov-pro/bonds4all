package com.shahinnazarov.paribas.service.impl;

import com.shahinnazarov.paribas.db.dao.BondHistoryDao;
import com.shahinnazarov.paribas.db.entity.BondHistory;
import com.shahinnazarov.paribas.service.BondHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collection;

@Service
public class BondHistoryServiceImpl implements BondHistoryService {
    @Autowired
    private BondHistoryDao bondHistoryDao;

    @Async
    @Transactional
    @Override
    public void save(BondHistory bondHistory) {
        bondHistory.setCreated(LocalDateTime.now());
        bondHistoryDao.save(bondHistory);
    }

    @Override
    public Collection<BondHistory> listHistoriesByBondReference(String reference) {
        return bondHistoryDao.listHistoriesByBondReference(reference);
    }
}
