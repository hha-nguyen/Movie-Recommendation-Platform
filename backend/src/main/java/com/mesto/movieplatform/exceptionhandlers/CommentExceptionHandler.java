package com.mesto.movieplatform.exceptionhandlers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.mesto.movieplatform.exceptions.CommentDetailsNotFoundException;

@ControllerAdvice
public class CommentExceptionHandler {

    @ExceptionHandler(value = CommentDetailsNotFoundException.class)
    public ResponseEntity HandleCommentDetailNotFoundException() {
        return new ResponseEntity("Comment does not exist with the passed value", HttpStatus.BAD_REQUEST);
    }
}
