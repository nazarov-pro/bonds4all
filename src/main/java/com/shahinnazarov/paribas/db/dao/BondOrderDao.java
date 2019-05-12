package com.shahinnazarov.paribas.db.dao;

import com.shahinnazarov.paribas.db.entity.BondOrder;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Optional;

public interface BondOrderDao {
    void save(BondOrder bondOrder);
    long countOfOrdersByIpAndAfterDate(LocalDateTime localDateTime, String ipAddress);
    Collection<BondOrder> listBondsByIpAddress(String ipAddress);
    Optional<BondOrder> getBondOrderByReference(String orderReference);
}
