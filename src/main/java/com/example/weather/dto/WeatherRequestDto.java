package com.example.weather.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
public class WeatherRequestDto {
    private int cityId;

    private String cityName;

    private BigDecimal latitude;

    private BigDecimal longitude;
}
