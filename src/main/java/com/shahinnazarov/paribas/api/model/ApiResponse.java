package com.shahinnazarov.paribas.api.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;
import java.util.Collection;

@Data
public class ApiResponse<V extends Serializable> {
    private String status;
    private boolean singleResult = true;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private ApiError error;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private V value;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Collection<V> items;

    public void setError(ApiError error) {
        this.singleResult = true;
        this.error = error;
    }

    public void setValue(V value) {
        this.singleResult = true;
        this.value = value;
    }

    public void setItems(Collection<V> items) {
        this.singleResult = false;
        this.items = items;
    }
}
