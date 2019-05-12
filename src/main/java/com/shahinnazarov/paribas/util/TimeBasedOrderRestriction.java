package com.shahinnazarov.paribas.util;

import com.shahinnazarov.paribas.db.entity.BondOrder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.Clock;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Component
@NoArgsConstructor
@AllArgsConstructor
public class TimeBasedOrderRestriction implements OrderRestriction {

    @Value("${app.limit.daily.amount}")
    private int maxAmount;
    @Value("${app.limit.daily.start}")
    private String start;
    @Value("${app.limit.daily.end}")
    private String end;

    //For testing purposes only
    private Clock clock = Clock.systemDefaultZone();

    @Override
    public synchronized boolean verify(BondOrder bondOrder) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");

        LocalTime startFrom = LocalTime.parse(start, formatter);
        LocalTime endTo = LocalTime.parse(end, formatter);

        LocalTime now = LocalTime.now(clock);
        if (bondOrder.getAmount().compareTo(BigDecimal.valueOf(maxAmount)) > 0  &&
                        ( now.isAfter(startFrom) || now.isBefore(endTo) )
        ) {
            throw new GeneralRestrictionException(Errors.TIME_LIMIT_EXCEED.getTitle(),
                    String.format(Errors.TIME_LIMIT_EXCEED.getDescription(), maxAmount, start, end));
        }
        return true;
    }
}
