package com.example.weather.api;

import com.example.weather.dto.OpenWeatherResponseDto;
import com.example.weather.dto.WeatherRequestDto;
import com.fasterxml.jackson.databind.ObjectMapper;

public class OpenWeatherClientAPI {

    private static final String BASE_URL = "https://api.openweathermap.org/data/2.5/weather?";
    private static final String LAT = "lat=";
    private static final String LON = "&lon=";
    private static final String TOKEN = "&appid=";
    private static final String UNITS = "&units=metric";

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public OpenWeatherResponseDto makeCall(WeatherRequestDto entity){

    }


}
