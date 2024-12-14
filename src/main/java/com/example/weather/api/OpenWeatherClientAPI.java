package com.example.weather.api;

import com.example.weather.dto.OpenWeatherResponseDto;
import com.example.weather.dto.WeatherRequestDto;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Component
public class OpenWeatherClientAPI {

    private static final String BASE_URL = "https://api.openweathermap.org/data/2.5/weather?";
    private static final String LAT = "lat=";
    private static final String LON = "&lon=";
    private static final String TOKEN = "&appid=";
    private static final String UNITS = "&units=metric";

    private static final String API_TOKEN = "51f95a918327f9685a464ab9714672a2";

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public OpenWeatherResponseDto makeCall(WeatherRequestDto entity){
        HttpResponse<String> resp;
        HttpClient client = HttpClient.newHttpClient();

        HttpRequest req = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + LAT + entity.getLatitude().toString() + LON + entity.getLongitude().toString() + TOKEN + API_TOKEN + UNITS))
                .GET()
                .build();

        try {
            resp = client.send(req, HttpResponse.BodyHandlers.ofString());

            OpenWeatherResponseDto response = objectMapper.readValue(resp.body(), new TypeReference<>(){});
            response.setCityId(entity.getCityId());
            response.setCityName(entity.getCityName());

            return response;
        }catch (Exception e){
            throw new RuntimeException();
        }

    }


}
