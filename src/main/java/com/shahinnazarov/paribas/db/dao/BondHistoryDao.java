package com.shahinnazarov.paribas.db.dao;

import com.shahinnazarov.paribas.db.entity.BondHistory;

import java.util.Collection;

public interface BondHistoryDao {
    void save(BondHistory bondHistory);
    Collection<BondHistory> listHistoriesByBondReference(String reference);
}
