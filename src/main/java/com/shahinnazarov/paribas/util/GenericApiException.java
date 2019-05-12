package com.shahinnazarov.paribas.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class GenericApiException extends RuntimeException {
    private String title;
    private String description;

}
