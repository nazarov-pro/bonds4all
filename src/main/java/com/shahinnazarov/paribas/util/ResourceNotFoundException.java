package com.shahinnazarov.paribas.util;

import lombok.EqualsAndHashCode;


@EqualsAndHashCode(callSuper = false)
public class ResourceNotFoundException extends GenericApiException {

    public ResourceNotFoundException(String title, String description) {
        super(title, description);
    }
}
