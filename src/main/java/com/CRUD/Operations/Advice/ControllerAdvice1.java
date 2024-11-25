package com.CRUD.Operations.Advice;

import com.CRUD.Operations.Service.ErrorResponse;
import com.CRUD.Operations.exceptions.EmptyInputException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class ControllerAdvice1 {
    @ExceptionHandler(EmptyInputException.class)
    public ResponseEntity<?> handleProductNotFoundException(EmptyInputException exception){
        ErrorResponse productNotFound = new ErrorResponse(LocalDateTime.now(), exception.getMessage(), "Product Not Found");
        return new ResponseEntity<>(productNotFound, HttpStatus.NOT_FOUND);
    }
}
