package com.example.weather.controller;

import com.example.weather.dto.GeocodingResponseDto;
import com.example.weather.dto.OpenWeatherResponseDto;
import com.example.weather.exception.UnauthorizedException;
import com.example.weather.exception.UserNotFoundExcepiton;
import com.example.weather.service.CityWeatherService;
import com.example.weather.service.SessionService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


@Controller
@RequiredArgsConstructor
public class HomeController {

    private final CityWeatherService weatherService;
    private final SessionService sessionService;

    @GetMapping("/home")
    public String loadHomePage(@CookieValue(name = "JSESSIONID", required = false) String sessionId, Model model){
        if (sessionId == null || sessionId.isEmpty()){
            throw new UnauthorizedException("Session cookie is missing");
        }

        List<OpenWeatherResponseDto> citiesWeather;

        try {
            citiesWeather = weatherService.getWeatherInCities(sessionId);
        }catch (RuntimeException e){
            throw new UserNotFoundExcepiton("");
        }

        model.addAttribute("username",sessionService.getSessionUser(sessionId).getUsername());
        model.addAttribute("weatherCities",citiesWeather);

        return "home";
    }

    @GetMapping("/found-locations")
    public String foundLocation(@RequestParam(name = "cityName", required = false) String cityName, Model model){
        if (cityName == null || cityName.isBlank()) {
           throw new RuntimeException();
        }

        List<GeocodingResponseDto> foundCities;

        try {
            foundCities = weatherService.getCityByName(cityName);
        }catch (RuntimeException e){
            throw new RuntimeException();
        }

        if (foundCities != null && foundCities.isEmpty()) {
            throw new RuntimeException();
        }

        model.addAttribute("foundCities", foundCities);

        return "found-locations";
    }

}
