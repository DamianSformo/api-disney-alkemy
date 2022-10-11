package com.dh.characterservice.Exceptions;

public class BadRequestException extends Exception{

    public BadRequestException(String message) {
        super(message);
    }
}