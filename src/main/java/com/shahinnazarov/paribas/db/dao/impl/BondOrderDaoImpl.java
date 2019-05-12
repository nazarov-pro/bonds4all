package com.shahinnazarov.paribas.db.dao.impl;

import com.shahinnazarov.paribas.db.dao.BondOrderDao;
import com.shahinnazarov.paribas.db.entity.BondOrder;
import com.shahinnazarov.paribas.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Optional;

@Repository
public class BondOrderDaoImpl implements BondOrderDao {
    @Autowired
    private EntityManager entityManager;

    @Override
    public void save(BondOrder bondOrder) {
        entityManager.persist(bondOrder);
    }

    @Override
    public long countOfOrdersByIpAndAfterDate(LocalDateTime localDateTime, String ipAddress) {
        return (long) entityManager
                .createQuery(Constants.ORDER_COUNT_BY_IP_ADDRESS_AND_DATE)
                .setParameter("date", localDateTime)
                .setParameter("ip", ipAddress)
                .getSingleResult();
    }

    @Override
    public Collection<BondOrder> listBondsByIpAddress(String ipAddress) {
        return entityManager
                .createQuery(Constants.ORDER_LIST_BY_IP)
                .setParameter("ip", ipAddress)
                .getResultList();
    }

    @Override
    public Optional<BondOrder> getBondOrderByReference(String orderReference) {
        return Optional.ofNullable(entityManager.find(BondOrder.class, orderReference));
    }
}
