package com.shahinnazarov.paribas.util;

public interface Constants {
    String ORDER_COUNT_BY_IP_ADDRESS_AND_DATE = "select count(bo) from BondOrder bo where bo.created > :date and bo.ipAddress = :ip";
    String ORDER_LIST_BY_IP = "select bo from BondOrder bo where bo.ipAddress = :ip";
    String HISTORY_LIST_BY_BOND_REFERENCE = "select bh from BondHistory bh join bh.bondOrder bo where bo.id = :refKey";
}
