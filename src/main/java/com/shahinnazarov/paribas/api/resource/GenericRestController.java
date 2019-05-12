package com.shahinnazarov.paribas.api.resource;

import com.shahinnazarov.paribas.api.model.ApiResponse;

import java.io.Serializable;
import java.util.Collection;

public class GenericRestController {

    protected <T extends Serializable> ApiResponse<T> generateOkResponse(T data) {
        ApiResponse<T> apiResponse = new ApiResponse();
        apiResponse.setValue(data);
        apiResponse.setStatus("ok");
        return apiResponse;
    }

    protected <T extends Serializable> ApiResponse<T> generateOkResponse(Collection<T> items) {
        ApiResponse<T> apiResponse = new ApiResponse();
        apiResponse.setItems(items);
        apiResponse.setStatus("ok");
        return apiResponse;
    }
}
