package com.shahinnazarov.paribas.util;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
public class GeneralRestrictionException extends GenericApiException {

    public GeneralRestrictionException(String title, String description) {
        super(title, description);
    }
}
