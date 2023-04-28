package com.analytics.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;
import retrofit2.Response;

import java.io.IOException;

@ControllerAdvice
public class RestExceptionHandler {
    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<String> handleHttpError(ResponseStatusException ex) {
        String message = ex.getReason();
        return new ResponseEntity<>(message, ex.getStatus());
    }

    public static void parseAndThrowException(Response<?> response) {
        int error_code = response.code();
        try {
            String parsed_message = response.errorBody().string();

            if (error_code == 400) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, parsed_message);
            }
            if (error_code == 404) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, parsed_message);
            }
            if (error_code == 429) {
                throw new ResponseStatusException(HttpStatus.TOO_MANY_REQUESTS, parsed_message);
            }
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, parsed_message);
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Unable to retrieve error message");
        }
    }
}
