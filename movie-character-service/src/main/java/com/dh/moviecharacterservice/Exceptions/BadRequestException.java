package com.dh.moviecharacterservice.Exceptions;

public class BadRequestException extends Exception{

    public BadRequestException(String message) {
        super(message);
    }
}