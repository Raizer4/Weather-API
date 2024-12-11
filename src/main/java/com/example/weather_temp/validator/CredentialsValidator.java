package com.example.weather_temp.validator;

import com.example.weather_temp.dto.LoginDto;
import com.example.weather_temp.dto.RegistrationDto;
import com.example.weather_temp.exception.CredentialsException;
import lombok.experimental.UtilityClass;

@UtilityClass
public class CredentialsValidator {

    public static void validateData(LoginDto dto){
        if (dto.getUsername() == null || dto.getUsername().isBlank()) {
            throw new CredentialsException("Username can`t be empty");
        }

        if (dto.getPassword() == null || dto.getPassword().isBlank()) {
            throw new CredentialsException("Password can`t be empty");
        }
    }

    public static void validateData(RegistrationDto dto){

        if (dto.getUsername() == null || dto.getUsername().isBlank()){
            throw new CredentialsException("Username can`t be empty");
        }

        if (dto.getPassword() == null || dto.getPassword().isBlank() || dto.getConfirmPassword() == null || dto.getConfirmPassword().isBlank()){
            throw new CredentialsException("Password can`t be empty");
        }

        if (!dto.getPassword().equals(dto.getConfirmPassword())){
            throw new CredentialsException("Password are not equal");
        }

        if (!dto.getUsername().matches("[a-zA-Z0-9]+")){
            throw new CredentialsException("Username must contains only english letter");
        }

    }

}
