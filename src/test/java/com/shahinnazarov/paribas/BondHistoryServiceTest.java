package com.shahinnazarov.paribas;

import com.shahinnazarov.paribas.db.entity.BondHistory;
import com.shahinnazarov.paribas.db.entity.BondOrder;
import com.shahinnazarov.paribas.db.entity.ClientDetail;
import com.shahinnazarov.paribas.db.entity.enums.BondHistoryActions;
import com.shahinnazarov.paribas.service.BondHistoryService;
import com.shahinnazarov.paribas.service.BondOrderService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collection;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {Bonds4AllApplication.class})
public class BondHistoryServiceTest {
    @Autowired
    private BondOrderService bondOrderService;

    @Autowired
    private BondHistoryService bondHistoryService;

    private BondOrder save() {
        BondOrder bondOrder = new BondOrder();
        ClientDetail clientDetail = new ClientDetail();
        clientDetail.setFirstName("Shahin");
        clientDetail.setLastName("Nazarov");
//        clientDetail.setSecretKey("123TTuuuuhg");
        bondOrder.setClientDetail(clientDetail);
        bondOrder.setIpAddress("127.0.0.1");
        bondOrder.setAmount(new BigDecimal(1000));
        bondOrder.setTermLength(5);
        BondOrder savedBond = bondOrderService.save(bondOrder);
        return savedBond;
    }

    private BondHistory saveBondHistory(BondOrder bondOrder) {
        BondHistory bondHistory = new BondHistory();
        bondHistory.setCreated(LocalDateTime.now());
        bondHistory.setBondOrder(bondOrder);
        bondHistory.setAction(BondHistoryActions.CREATED);
        bondHistoryService.save(bondHistory);
        return bondHistory;
    }

    @Test
    public void saveBondHistory() {
        BondOrder savedBond = save();
        Assert.assertNotNull(savedBond.getId());
        Assert.assertNotNull(saveBondHistory(savedBond).getId());
    }


    @Test
    public void listHistoriesByReference() {
        BondOrder savedBond = save();
        BondHistory bondHistory = saveBondHistory(savedBond);
        Collection<BondHistory> listBondsByIpAddress = bondHistoryService.listHistoriesByBondReference(savedBond.getId());
        Assert.assertTrue(listBondsByIpAddress.size() > 0);
    }
}
