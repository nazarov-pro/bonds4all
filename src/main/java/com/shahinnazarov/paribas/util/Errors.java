package com.shahinnazarov.paribas.util;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Errors {
    TIME_LIMIT_EXCEED("restriction.time", "The amount of bond shouldn't be greater than %d during %s - %s"),
    IP_LIMIT_EXCEED("restriction.ip", "The count of bonds shouldn't be greater than %d with the same IP Address in %d day(s)."),
    TERM_SIZE_NOT_VALID("restriction.term.length", "The minimum term length for a bond is %d years."),
    RESOURCE_NOT_FOUND("resource.not.found", "The resource represents %s not found."),
    ;

    private String title;
    private String description;
}
