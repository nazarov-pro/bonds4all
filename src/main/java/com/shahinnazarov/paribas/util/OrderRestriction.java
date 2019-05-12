package com.shahinnazarov.paribas.util;

import com.shahinnazarov.paribas.db.entity.BondOrder;

public interface OrderRestriction {
    boolean verify(BondOrder bondOrder);
}
