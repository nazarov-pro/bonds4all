package com.shahinnazarov.paribas.util;

import com.shahinnazarov.paribas.db.entity.BondOrder;
import com.shahinnazarov.paribas.service.BondOrderService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@NoArgsConstructor
@AllArgsConstructor
public class IpBasedOrderRestriction implements OrderRestriction {
    @Value("${app.limit.ip.max}")
    private int maxIpAddresses;

    @Value("${app.limit.ip.max.days}")
    private int maxAcceptableDays;

    @Autowired
    private BondOrderService bondOrderService;

    @Autowired
    public IpBasedOrderRestriction(BondOrderService bondOrderService) {
        this.bondOrderService = bondOrderService;
    }

    @Override
    public synchronized boolean verify(BondOrder bondOrder) {
        LocalDateTime localDateTime = LocalDateTime.now().minusDays(maxAcceptableDays);
        long count = bondOrderService.countOfOrdersByIpAndAfterDate(localDateTime, bondOrder.getIpAddress());
        if (count >= maxIpAddresses) {
            throw new GeneralRestrictionException(Errors.IP_LIMIT_EXCEED.getTitle(),
                    String.format(Errors.IP_LIMIT_EXCEED.getDescription(), maxIpAddresses, maxAcceptableDays));
        }
        return true;
    }
}
