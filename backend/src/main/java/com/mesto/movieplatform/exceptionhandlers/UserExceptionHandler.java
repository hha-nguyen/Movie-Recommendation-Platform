package com.mesto.movieplatform.exceptionhandlers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.mesto.movieplatform.exceptions.UserDetailsNotFoundException;

@ControllerAdvice
public class UserExceptionHandler {

    @ExceptionHandler(value = UserDetailsNotFoundException.class)
    public ResponseEntity HandleUserExceptionHandler() {
        return new ResponseEntity("User does not exists with id ", HttpStatus.BAD_REQUEST);
    }
}
