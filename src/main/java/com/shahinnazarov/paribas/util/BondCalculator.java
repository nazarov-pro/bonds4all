package com.shahinnazarov.paribas.util;

import java.math.BigDecimal;

public interface BondCalculator {
    BigDecimal calculateCouponAmount(BigDecimal amount);

    BigDecimal finalSum(int termLength, BigDecimal amount);

    BigDecimal finalSum(int termLength, BigDecimal couponAmount, BigDecimal amount);

    BigDecimal adjustCoupon(BigDecimal couponAmount, int oldTermLength, int newTermLength);
}
