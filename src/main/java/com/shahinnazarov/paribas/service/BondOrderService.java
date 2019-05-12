package com.shahinnazarov.paribas.service;

import com.shahinnazarov.paribas.db.entity.BondOrder;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Optional;

public interface BondOrderService {
    BondOrder save(BondOrder bondOrder);
    long countOfOrdersByIpAndAfterDate(LocalDateTime localDateTime, String ipAddress);
    Collection<BondOrder> listBondsByIpAddress(String ipAddress);
    Optional<BondOrder> getBondOrderByReference(String orderReference);
    void adjust(BondOrder bondOrder, int newTermLength);
}
