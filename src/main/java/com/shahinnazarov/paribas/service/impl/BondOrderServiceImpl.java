package com.shahinnazarov.paribas.service.impl;

import com.shahinnazarov.paribas.db.dao.BondHistoryDao;
import com.shahinnazarov.paribas.db.dao.BondOrderDao;
import com.shahinnazarov.paribas.db.entity.BondHistory;
import com.shahinnazarov.paribas.db.entity.BondHistoryItem;
import com.shahinnazarov.paribas.db.entity.BondOrder;
import com.shahinnazarov.paribas.db.entity.enums.BondHistoryActions;
import com.shahinnazarov.paribas.db.entity.enums.BondHistoryItemTypes;
import com.shahinnazarov.paribas.service.BondHistoryService;
import com.shahinnazarov.paribas.service.BondOrderService;
import com.shahinnazarov.paribas.util.BondCalculator;
import com.shahinnazarov.paribas.util.OrderRestriction;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class BondOrderServiceImpl implements BondOrderService {

    @Autowired
    private Collection<OrderRestriction> orderRestrictions;
    @Autowired
    private BondCalculator bondCalculator;
    @Autowired
    private BondOrderDao bondOrderDao;

    @Autowired
    private BondHistoryDao bondHistoryDao;

    @Transactional
    @Override
    public BondOrder save(BondOrder bondOrder) {
        orderRestrictions.forEach(orderRestriction -> orderRestriction.verify(bondOrder));
        bondOrder.setFinalSum(bondCalculator.finalSum(bondOrder.getTermLength(), bondOrder.getAmount()));

        LocalDateTime now = LocalDateTime.now();
        bondOrder.setCreated(now);
        bondOrder.setValid(now.plusYears(bondOrder.getTermLength()));
        bondOrder.setCouponAmount(bondCalculator.calculateCouponAmount(bondOrder.getAmount()));
        bondOrder.setId(UUID.randomUUID().toString());
        bondOrderDao.save(bondOrder);

        BondHistory bondHistory = new BondHistory();
        bondHistory.setAction(BondHistoryActions.CREATED);
        bondHistory.setBondOrder(bondOrder);
        bondHistoryDao.save(bondHistory);

        return bondOrder;
    }

    @Override
    public long countOfOrdersByIpAndAfterDate(LocalDateTime localDateTime, String ipAddress) {
        return bondOrderDao.countOfOrdersByIpAndAfterDate(localDateTime, ipAddress);
    }

    @Override
    public Collection<BondOrder> listBondsByIpAddress(String ipAddress) {
        return bondOrderDao.listBondsByIpAddress(ipAddress);
    }

    @Override
    public Optional<BondOrder> getBondOrderByReference(String orderReference) {
        return bondOrderDao.getBondOrderByReference(orderReference);
    }

    @Override
    @Transactional
    public void adjust(BondOrder bondOrder, int newTermLength) {
        if (bondOrder.getTermLength() == newTermLength) {
            return;
        }

        BigDecimal newCouponAmount = bondCalculator.adjustCoupon(bondOrder.getCouponAmount(),
                bondOrder.getTermLength(), newTermLength);

        BondHistory bondHistory = new BondHistory();
        bondHistory.setBondOrder(bondOrder);
        bondHistory.setAction(BondHistoryActions.UPDATED);
        bondHistory.setCreated(LocalDateTime.now());

        BondHistoryItem bondHistoryItem = new BondHistoryItem();
        bondHistoryItem.setOldValue(String.valueOf(bondOrder.getTermLength()));
        bondHistoryItem.setNewValue(String.valueOf(newTermLength));
        bondHistoryItem.setBondHistory(bondHistory);
        bondHistoryItem.setType(BondHistoryItemTypes.ORDER_TERM_LENGTH);

        BondHistoryItem bondHistoryItem2 = new BondHistoryItem();
        bondHistoryItem2.setOldValue(bondOrder.getCouponAmount().toString());
        bondHistoryItem2.setNewValue(newCouponAmount.toString());
        bondHistoryItem2.setBondHistory(bondHistory);
        bondHistoryItem2.setType(BondHistoryItemTypes.ORDER_COUPON_AMOUNT);

        bondHistory.setItems(Arrays.asList(bondHistoryItem, bondHistoryItem2));
        bondHistory.setBondOrder(bondOrder);
        bondHistoryDao.save(bondHistory);

        bondOrder.setTermLength(newTermLength);
        bondOrder.setCouponAmount(newCouponAmount);
        bondOrder.setValid(bondOrder.getCreated().plusYears(newTermLength));
        bondOrder.setFinalSum(bondCalculator.finalSum(newTermLength, newCouponAmount, bondOrder.getAmount()));
        bondOrderDao.save(bondOrder);
    }
}
