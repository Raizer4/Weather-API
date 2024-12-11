package com.example.weather.service;

import com.example.weather.dto.OpenWeatherResponseDto;
import com.example.weather.dto.WeatherRequestDto;
import com.example.weather.entity.Location;
import com.example.weather.entity.User;
import com.example.weather.exception.ConnectionErrorException;
import com.example.weather.repository.LocationRepository;
import com.example.weather.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CityWeatherService {

    private final SessionService sessionService;
    private final LocationRepository locationRepository;
    private final UserRepository userRepository;


    public List<OpenWeatherResponseDto> getWeatherInCities(String sessionId) {
        User user = sessionService.getSessionUser(sessionId);

        List<OpenWeatherResponseDto> response = new ArrayList<>();

        try {
            for (Location location : user.getLocations()){
                WeatherRequestDto weatherRequesDto = WeatherRequestDto.builder()
                        .cityId(location.getId())
                        .cityName(location.getName())
                        .latitude(location.getLatitude())
                        .longitude(location.getLongitude())
                        .build();

                response.add();
            }
        }catch (ConnectionErrorException e){
            throw new RuntimeException("Wrong Weather API call");
        }

        return response;
    }


}
