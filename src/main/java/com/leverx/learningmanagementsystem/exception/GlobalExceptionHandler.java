package com.leverx.learningmanagementsystem.exception;

import com.leverx.learningmanagementsystem.dto.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse exceptionHandler(Exception ex) {
        log.error(ex.getMessage());
        return new ErrorResponse("Something went wrong :(");
    }

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse entityNotFoundExceptionHandler(EntityNotFoundException ex) {
        return new ErrorResponse(ex.getMessage());
    }

    @ExceptionHandler(EntityValidationException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse entityValidationExceptionHandler(EntityValidationException ex){
        return new ErrorResponse(ex.getMessage());
    }

    @ExceptionHandler(PurchaseDeniedException.class)
    @ResponseStatus(HttpStatus.PAYMENT_REQUIRED)
    public ErrorResponse purchaseDeniedExceptionHandler(PurchaseDeniedException ex){
        return new ErrorResponse(ex.getMessage());
    }

}
