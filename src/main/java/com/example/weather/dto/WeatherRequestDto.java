package com.example.weather.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class WeatherRequestDto {
    private int cityId;

    private String cityName;

    private BigDecimal latitude;

    private BigDecimal longitude;
}
