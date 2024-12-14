package com.example.weather.api;

import com.example.weather.dto.GeocodingResponseDto;
import com.example.weather.dto.WeatherRequestDto;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

@Component
public class GeoCodingClientAPI {

    private static final String BASE_URL = "https://api.openweathermap.org/geo/1.0/direct?q=";
    private static final String LIMIT_AND_TOKEN = "&limit=10&appid=";
    private static final String API_TOKEN = "51f95a918327f9685a464ab9714672a2";

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public List<GeocodingResponseDto> makeCall(WeatherRequestDto entity){
        HttpResponse<String> resp;
        HttpClient client = HttpClient.newHttpClient();

        HttpRequest req = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + entity.getCityName() + LIMIT_AND_TOKEN + API_TOKEN))
                .GET()
                .build();

        try {
            resp = client.send(req, HttpResponse.BodyHandlers.ofString());
            return objectMapper.readValue(resp.body(), new TypeReference<>() {});
        }catch (Exception e){
            throw new RuntimeException(e);
        }

    }

}
