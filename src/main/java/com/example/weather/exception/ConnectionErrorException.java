package com.example.weather.exception;

public class ConnectionErrorException extends RuntimeException {
    public ConnectionErrorException(String message){
        super(message);
    }
}
