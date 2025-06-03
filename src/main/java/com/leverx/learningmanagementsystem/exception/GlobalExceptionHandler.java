package com.leverx.learningmanagementsystem.exception;

import com.leverx.learningmanagementsystem.lesson.exception.LessonRequestValidationException;
import com.leverx.learningmanagementsystem.web.oauth2.exception.OAuth2TokenClientBadResponseException;
import com.leverx.learningmanagementsystem.course.exception.EntityValidationException;
import com.leverx.learningmanagementsystem.email.smtpprovider.exception.FeatureFlagServiceBadResponseException;
import com.leverx.learningmanagementsystem.exception.dto.ErrorResponseDto;
import com.leverx.learningmanagementsystem.payments.exception.NotEnoughCoinsException;
import com.leverx.learningmanagementsystem.payments.exception.StudentAlreadyEnrolledException;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.PAYMENT_REQUIRED;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.SERVICE_UNAVAILABLE;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseStatus(INTERNAL_SERVER_ERROR)
    public ErrorResponseDto handleException(Exception ex) {
        log.error("Unexpected error occurred", ex);
        return new ErrorResponseDto(ex.getMessage());
    }

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(NOT_FOUND)
    public ErrorResponseDto handleEntityNotFoundException(EntityNotFoundException ex) {
        return new ErrorResponseDto(ex.getMessage());
    }

    @ExceptionHandler(EntityValidationException.class)
    @ResponseStatus(BAD_REQUEST)
    public ErrorResponseDto handleEntityValidationException(EntityValidationException ex){
        return new ErrorResponseDto(ex.getMessage());
    }

    @ExceptionHandler(NotEnoughCoinsException.class)
    @ResponseStatus(PAYMENT_REQUIRED)
    public ErrorResponseDto handleNotEnoughCoinsException(NotEnoughCoinsException ex){
        return new ErrorResponseDto(ex.getMessage());
    }

    @ExceptionHandler(StudentAlreadyEnrolledException.class)
    @ResponseStatus(BAD_REQUEST)
    public ErrorResponseDto handleStudentAlreadyEnrolledException(StudentAlreadyEnrolledException ex){
        return new ErrorResponseDto(ex.getMessage());
    }

    @ExceptionHandler(FeatureFlagServiceBadResponseException.class)
    @ResponseStatus(SERVICE_UNAVAILABLE)
    public ErrorResponseDto handleFeatureFlagServiceBadResponseException(FeatureFlagServiceBadResponseException ex){
        return new ErrorResponseDto(ex.getMessage());
    }

    @ExceptionHandler(OAuth2TokenClientBadResponseException.class)
    @ResponseStatus(BAD_REQUEST)
    public ErrorResponseDto handleOAuth2TokenClientBadResponseException(OAuth2TokenClientBadResponseException ex){
        log.error("Unexpected error occurred", ex);
        return new ErrorResponseDto(ex.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(BAD_REQUEST)
    public ErrorResponseDto handleMethodArgumentNotValidException(MethodArgumentNotValidException ex){
        return new ErrorResponseDto(ex.getMessage());
    }

    @ExceptionHandler(LessonRequestValidationException.class)
    @ResponseStatus(BAD_REQUEST)
    public ErrorResponseDto handleLessonRequestValidationException(LessonRequestValidationException ex){
        return new ErrorResponseDto(ex.getMessage());
    }
}
