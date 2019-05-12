package com.shahinnazarov.paribas.db.entity.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum BondHistoryItemTypes {
    ORDER_TERM_LENGTH("order.term.length"),
    ORDER_COUPON_AMOUNT("order.coupon.amount"),
    ORDER_AMOUNT("order.amount");

    private String key;
}
