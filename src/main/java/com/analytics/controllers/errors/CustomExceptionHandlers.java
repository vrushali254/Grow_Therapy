package com.analytics.controllers.errors;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class CustomExceptionHandlers {

    @ResponseBody
    @ExceptionHandler(CustomNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String resourceNotFound(CustomNotFoundException e) {
        return e.getMessage();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    @ExceptionHandler(DuplicateResourceException.class)
    public String resourceAlreadyExists(DuplicateResourceException e) {
        return e.getMessage();
    }

//    @ResponseStatus(HttpStatus.TOO_MANY_REQUESTS)
//    @ResponseBody
//    @ExceptionHandler(DuplicateResourceException.class)
//    public String tooManyRequests(DuplicateResourceException e) {
//        return e.getMessage();
//    }

}
