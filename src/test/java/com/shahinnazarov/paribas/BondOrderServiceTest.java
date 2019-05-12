package com.shahinnazarov.paribas;

import com.shahinnazarov.paribas.db.entity.BondOrder;
import com.shahinnazarov.paribas.db.entity.ClientDetail;
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
public class BondOrderServiceTest {
    @Autowired
    private BondOrderService bondOrderService;

    @Test
    public void saveBond(){
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
        Assert.assertNotNull(savedBond.getId());
    }

    @Test
    public void countOfDailyBond(){
        saveBond();
        long l = bondOrderService.countOfOrdersByIpAndAfterDate(LocalDateTime.now().minusDays(1), "127.0.0.1");
        Assert.assertTrue(l > 0);
    }

    @Test
    public void listOrdersByIp(){
        saveBond();
        Collection<BondOrder> listBondsByIpAddress = bondOrderService.listBondsByIpAddress("127.0.0.1");
        Assert.assertTrue(listBondsByIpAddress.size() > 0);
    }
}
