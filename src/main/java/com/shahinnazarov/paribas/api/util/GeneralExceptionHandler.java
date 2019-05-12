package com.shahinnazarov.paribas.api.util;

import com.shahinnazarov.paribas.api.model.ApiError;
import com.shahinnazarov.paribas.api.model.ApiResponse;
import com.shahinnazarov.paribas.util.GeneralRestrictionException;
import com.shahinnazarov.paribas.util.GenericApiException;
import com.shahinnazarov.paribas.util.ResourceNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GeneralExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {GeneralRestrictionException.class, ResourceNotFoundException.class})
    protected ResponseEntity<ApiResponse> handleGenericException(GenericApiException ex, WebRequest request) {
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setStatus("error");

        ApiError apiError = new ApiError();
        apiError.setDescription(ex.getDescription());
        apiError.setTitle(ex.getTitle());

        apiResponse.setError(apiError);

        return ResponseEntity.badRequest().body(apiResponse);
    }
}
