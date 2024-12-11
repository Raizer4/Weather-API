package com.example.weather_temp.controller;

import com.example.weather_temp.dto.OpenWeatherResponseDto;
import com.example.weather_temp.exception.UnauthorizedException;
import com.example.weather_temp.exception.UserNotFoundExcepiton;
import com.example.weather_temp.service.CityWeatherService;
import com.example.weather_temp.service.SessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;

import java.util.List;


@Controller
@RequiredArgsConstructor
public class HomeController {

    private final CityWeatherService weatherService;
    private final SessionService sessionService;

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


}
