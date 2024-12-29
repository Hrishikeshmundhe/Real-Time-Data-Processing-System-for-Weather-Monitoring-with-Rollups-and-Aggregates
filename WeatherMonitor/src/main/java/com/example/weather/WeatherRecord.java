package com.example.weather;

import java.time.LocalDateTime;

public class WeatherRecord {
    private double temperature;
    private String weatherCondition;
    private LocalDateTime timestamp;

    public WeatherRecord(double temperature, String weatherCondition, LocalDateTime timestamp) {
        this.temperature = temperature;
        this.weatherCondition = weatherCondition;
        this.timestamp = timestamp;
    }

    public double getTemperature() {
        return temperature;
    }

    public String getWeatherCondition() {
        return weatherCondition;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }
}
