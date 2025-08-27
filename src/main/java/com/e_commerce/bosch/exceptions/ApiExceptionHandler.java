package com.e_commerce.bosch.exceptions;


import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ApiExceptionDTO> handleApiException(ApiException ex) {

        return ResponseEntity.status(ex.getHttpStatus())
                .contentType(MediaType.APPLICATION_JSON)
                .body(ApiExceptionDTO.builder()
                        .errorMessage(ex.getErrorMessage())
                        .httpStatus(ex.getHttpStatus())
                        .build());
    }
}