package com.leverx.learningmanagementsystem.exception;

import com.leverx.learningmanagementsystem.dto.ErrorDto;
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
    public ErrorDto exceptionHandler(Exception ex){
        log.error(ex.getMessage());
        return new ErrorDto("Something went wrong :(");
    }

    @ExceptionHandler(CourseNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorDto courseNotFoundHandler(CourseNotFoundException ex){
        return new ErrorDto(ex.getMessage());
    }

    @ExceptionHandler(CourseSettingsNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorDto courseSettingsNotFoundHandler(CourseSettingsNotFoundException ex){
        return new ErrorDto(ex.getMessage());
    }

    @ExceptionHandler(LessonNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorDto lessonNotFoundHandler(LessonNotFoundException ex){
        return new ErrorDto(ex.getMessage());
    }

    @ExceptionHandler(StudentNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorDto studentNotFoundHandler(StudentNotFoundException ex){
        return new ErrorDto(ex.getMessage());
    }

    @ExceptionHandler(EmailAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorDto studentNotFoundHandler(EmailAlreadyExistsException ex){
        return new ErrorDto(ex.getMessage());
    }

}
