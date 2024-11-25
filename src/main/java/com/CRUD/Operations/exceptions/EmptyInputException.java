package com.CRUD.Operations.exceptions;

public class EmptyInputException extends RuntimeException {
    public EmptyInputException(String message){
        super(message);
    }
}
