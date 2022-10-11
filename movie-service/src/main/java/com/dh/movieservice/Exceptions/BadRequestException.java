package com.dh.movieservice.Exceptions;

public class BadRequestException extends Exception{

    public BadRequestException(String message) {
        super(message);
    }
}