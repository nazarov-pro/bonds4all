package com.shahinnazarov.paribas.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

import static com.shahinnazarov.paribas.util.Errors.TERM_SIZE_NOT_VALID;

@Component
public class GeneralBondCalculator implements BondCalculator {

    @Value("${app.coupon.interest.rate.per.year}")
    private int interestRate;

    @Value("${app.coupon.minimum.term.years}")
    private int minTermLength;

    @Value("${app.coupon.adjust.increase.term}")
    private int termIncreasePercentage;

    @Value("${app.coupon.adjust.decrease.term}")
    private int termDecreasePercentage;

    @Override
    public BigDecimal calculateCouponAmount(BigDecimal amount) {
        return calculatePercentage(amount, interestRate);
    }

    @Override
    public BigDecimal finalSum(final int termLength, final BigDecimal amount) {
        validateTermLength(termLength);

        BigDecimal result = calculateCouponAmount(amount)
                .multiply(BigDecimal.valueOf(termLength));
        return result.add(amount);
    }

    @Override
    public BigDecimal finalSum(int termLength, BigDecimal couponAmount, BigDecimal amount) {
        BigDecimal result = couponAmount
                .multiply(BigDecimal.valueOf(termLength));
        return result.add(amount);
    }

    @Override
    public BigDecimal adjustCoupon(BigDecimal couponAmount, int oldTermLength, int newTermLength) {
        validateTermLength(newTermLength);
        if (oldTermLength < newTermLength) {
            return couponAmount.add(calculatePercentage(couponAmount, termIncreasePercentage));
        } else if (oldTermLength > newTermLength) {
            return couponAmount.add(calculatePercentage(couponAmount, termDecreasePercentage));
        } else {
            return couponAmount;
        }
    }

    private BigDecimal calculatePercentage(BigDecimal decimal, int percentage) {
        if (percentage == 0) {
            return BigDecimal.ZERO;
        } else if (percentage > 0) {
            return decimal.multiply(BigDecimal.valueOf(percentage)).divide(BigDecimal.valueOf(100));
        } else {
            return decimal.multiply(BigDecimal.valueOf(-percentage)).divide(BigDecimal.valueOf(100)).negate();
        }
    }

    private void validateTermLength(int termLength){
        if (termLength < minTermLength) {
            throw new GeneralRestrictionException(TERM_SIZE_NOT_VALID.getTitle(),
                    String.format(TERM_SIZE_NOT_VALID.getDescription(), minTermLength));
        }
    }
}
