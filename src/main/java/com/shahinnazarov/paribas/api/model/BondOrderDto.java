package com.shahinnazarov.paribas.api.model;

import com.shahinnazarov.paribas.db.entity.ClientDetail;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class BondOrderDto implements Serializable {
    private static final long serialVersionUID = 766716959456650207L;
    @NotNull
    private ClientDetail clientDetail;
    @NotNull
    @Positive
    private int termLength;
    @NotNull
    @Positive
    private BigDecimal amount;
}
