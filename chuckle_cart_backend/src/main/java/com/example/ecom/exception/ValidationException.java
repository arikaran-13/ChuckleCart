package com.example.ecom.exception;

public class ValidationException extends RuntimeException{

    public ValidationException(String message){
        super(message);
    }
}
