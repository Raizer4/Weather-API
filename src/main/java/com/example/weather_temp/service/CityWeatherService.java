package com.example.weather_temp.service;

import com.example.weather_temp.dto.OpenWeatherResponseDto;
import com.example.weather_temp.repository.LocationRepository;
import com.example.weather_temp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CityWeatherService {

    private final SessionService sessionService;
    private final LocationRepository locationRepository;
    private final UserRepository userRepository;


    public List<OpenWeatherResponseDto> getWeatherInCities(String sessionId) {
        return null;
    }


}
