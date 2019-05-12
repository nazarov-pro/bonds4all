package com.shahinnazarov.paribas;

import com.shahinnazarov.paribas.db.entity.BondOrder;
import com.shahinnazarov.paribas.service.BondOrderService;
import com.shahinnazarov.paribas.util.GeneralRestrictionException;
import com.shahinnazarov.paribas.util.IpBasedOrderRestriction;
import com.shahinnazarov.paribas.util.TimeBasedOrderRestriction;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;
import java.time.*;

@ActiveProfiles("test")
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {Bonds4AllApplication.class})
public class OrderRestrictionTest {

    private TimeBasedOrderRestriction timeBasedOrderRestriction;
    private ZoneId zoneId = ZoneId.systemDefault();

    @Autowired
    private BondOrderService bondOrderService;

    @Autowired
    private IpBasedOrderRestriction ipBasedOrderRestriction;

    @Test
    public void timeBasedRestrictionDuringAllowedTime() {
        LocalDateTime localDateTime = LocalDateTime.of(LocalDate.now(), LocalTime.of(15, 25, 55));
        timeBasedOrderRestriction = new TimeBasedOrderRestriction(1000,
                "22:00:00", "06:00:00",
                Clock.fixed(localDateTime.atZone(zoneId).toInstant(), zoneId));

        BondOrder bondOrder = new BondOrder();
        bondOrder.setAmount(BigDecimal.valueOf(100));
        boolean result = timeBasedOrderRestriction.verify(bondOrder);
        Assert.assertTrue(result);

        bondOrder.setAmount(BigDecimal.valueOf(1001));
        result = timeBasedOrderRestriction.verify(bondOrder);

        Assert.assertTrue(result);
    }

    @Test(expected = GeneralRestrictionException.class)
    public void timeBasedRestrictionDuringRestrictedTime() {
        LocalDateTime localDateTime = LocalDateTime.of(LocalDate.now(), LocalTime.of(22, 25, 55));
        timeBasedOrderRestriction = new TimeBasedOrderRestriction(1000,
                "22:00:00", "06:00:00",
                Clock.fixed(localDateTime.atZone(zoneId).toInstant(), zoneId));

        BondOrder bondOrder = new BondOrder();
        bondOrder.setAmount(BigDecimal.valueOf(100));
        boolean result = timeBasedOrderRestriction.verify(bondOrder);
        Assert.assertTrue(result);

        bondOrder.setAmount(BigDecimal.valueOf(1001));
        result = timeBasedOrderRestriction.verify(bondOrder);

        Assert.assertTrue(result);
    }

    @Test
    public void ipBasedRestrictionNotMoreThanLimit() {
        String myIpAddress = "127.0.0.1";

        Mockito.when(bondOrderService.countOfOrdersByIpAndAfterDate(Mockito.any(LocalDateTime.class), Mockito.eq(myIpAddress)))
                .thenReturn(4L);

        BondOrder bondOrder = new BondOrder();
        bondOrder.setAmount(BigDecimal.valueOf(100));
        bondOrder.setIpAddress(myIpAddress);

        boolean result = ipBasedOrderRestriction.verify(bondOrder);
        Assert.assertTrue(result);

    }

    @Test(expected = GeneralRestrictionException.class)
    public void ipBasedRestrictionMoreThanLimit() {
        String myIpAddress = "127.0.0.1";

        Mockito.when(bondOrderService.countOfOrdersByIpAndAfterDate(Mockito.any(LocalDateTime.class), Mockito.eq(myIpAddress)))
                .thenReturn(5L);

        BondOrder bondOrder = new BondOrder();
        bondOrder.setAmount(BigDecimal.valueOf(100));
        bondOrder.setIpAddress(myIpAddress);

        boolean result = ipBasedOrderRestriction.verify(bondOrder);
        Assert.assertTrue(result);
    }
}
