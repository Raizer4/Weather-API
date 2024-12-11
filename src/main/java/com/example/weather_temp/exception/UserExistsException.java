package com.example.weather_temp.exception;

public class UserExistsException extends RuntimeException {

    public UserExistsException(String message){
        super(message);
    }

}
