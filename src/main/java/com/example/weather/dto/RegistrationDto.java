package com.example.weather.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class RegistrationDto {
    private String username;
    private String password;
    private String confirmPassword;
}
