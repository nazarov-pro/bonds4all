package com.shahinnazarov.paribas.api.model;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.io.Serializable;

@Data
public class BondOrderAdjustDto implements Serializable {
    private static final long serialVersionUID = 766716959456650207L;
    @NotNull
    @Positive
    private int newTermLength;
}
