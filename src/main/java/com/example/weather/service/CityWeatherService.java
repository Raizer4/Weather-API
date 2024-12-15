package com.example.weather.service;

import com.example.weather.api.GeoCodingClientAPI;
import com.example.weather.api.OpenWeatherClientAPI;
import com.example.weather.dto.GeocodingResponseDto;
import com.example.weather.dto.OpenWeatherResponseDto;
import com.example.weather.dto.WeatherRequestDto;
import com.example.weather.entity.Location;
import com.example.weather.entity.User;
import com.example.weather.exception.ConnectionErrorException;
import com.example.weather.exception.LocationException;
import com.example.weather.repository.LocationRepository;
import com.example.weather.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CityWeatherService {

    private final GeoCodingClientAPI geoCodingClientAPI;
    private final OpenWeatherClientAPI openWeatherClientAPI;
    private final SessionService sessionService;
    private final LocationRepository locationRepository;
    private final UserRepository userRepository;

    public List<GeocodingResponseDto> getCityByName(String cityName){
        try {
            WeatherRequestDto weatherRequestDto = WeatherRequestDto.builder()
                    .cityName(URLEncoder.encode(cityName, Charset.defaultCharset()))
                    .build();

            return geoCodingClientAPI.makeCall(weatherRequestDto);
        }catch (ConnectionErrorException e){
            throw new RuntimeException("Wrong Geocoding API call");
        }
    }

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

                response.add(openWeatherClientAPI.makeCall(weatherRequesDto));
            }
        }catch (ConnectionErrorException e){
            throw new RuntimeException("Wrong Weather API call");
        }

        return response;
    }

    public void saveCityToUserFavourites(WeatherRequestDto weatherRequestDto, String sessionId) {
        User user = sessionService.getSessionUser(sessionId);
        user.getLocations().add(addLocation(weatherRequestDto,user));
    }

    private Location addLocation(WeatherRequestDto weatherRequestDto, User user) {
        Location location = new Location().builder()
                .author(user)
                .name(weatherRequestDto.getCityName())
                .latitude(weatherRequestDto.getLatitude())
                .longitude(weatherRequestDto.getLongitude())
                .build();

        locationRepository.save(location);
        return location;
    }

}
