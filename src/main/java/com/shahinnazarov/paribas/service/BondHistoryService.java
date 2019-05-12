package com.shahinnazarov.paribas.service;

import com.shahinnazarov.paribas.db.entity.BondHistory;

import java.util.Collection;

public interface BondHistoryService {
    void save(BondHistory bondHistory);
    Collection<BondHistory> listHistoriesByBondReference(String reference);
}
