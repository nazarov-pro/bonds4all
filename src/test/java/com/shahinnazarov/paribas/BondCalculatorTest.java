package com.shahinnazarov.paribas;

import com.shahinnazarov.paribas.util.BondCalculator;
import com.shahinnazarov.paribas.util.GeneralRestrictionException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;

@ActiveProfiles("test")
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {Bonds4AllApplication.class})
public class BondCalculatorTest {
    @Autowired
    private BondCalculator bondCalculator;

    @Test(expected = GeneralRestrictionException.class)
    public void calculateInvalidTerms() {
        BigDecimal value = bondCalculator.finalSum(2, BigDecimal.valueOf(1000));
        Assert.assertEquals(value, BigDecimal.ZERO);
    }


    @Test
    public void calculateValidTerms() {
        //50$ per year * 5 + 1000 = 1250
        BigDecimal value = bondCalculator.finalSum(5, BigDecimal.valueOf(1000));
        Assert.assertEquals(value, new BigDecimal(1250));

        //60.75$ per year * 5 + 1250 = 1562.5
        value = bondCalculator.finalSum(5, BigDecimal.valueOf(1250));
        Assert.assertEquals(value, new BigDecimal(1562.5));
    }

    @Test
    public void adjustIncrease() {
        //50$ * 10 / 100 = 5 => 50 - 5 = 45
        BigDecimal value = bondCalculator.adjustCoupon(new BigDecimal(50), 7, 12);
        Assert.assertEquals(value, BigDecimal.valueOf(45));
    }

    @Test
    public void adjustDecrease() {
        //50$ * 0 / 100 = 0 => 50 - 0 = 50
        BigDecimal value = bondCalculator.adjustCoupon(new BigDecimal(50), 7, 6);
        Assert.assertEquals(value, BigDecimal.valueOf(50));
    }
}
