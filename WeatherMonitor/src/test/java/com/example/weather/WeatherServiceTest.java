package com.example.weather;

import com.example.database.DatabaseService;
import com.example.weather.WeatherService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import static org.mockito.Mockito.*;

@SpringBootTest(properties = {"weather.api.base-url=https://api.openweathermap.org/data/2.5/weather"})
@TestPropertySource(properties = "weather.api.key=575ca8ce4fe001b286f51403628ed8cd")
public class WeatherServiceTest {

    @InjectMocks
    private WeatherService weatherService;

    @Mock
    private DatabaseService databaseService;

    @Mock
    private WeatherAlert weatherAlert;

    private String apiKey = "575ca8ce4fe001b286f51403628ed8cd";
    @BeforeEach
    public void setup() {
        if (apiKey == null || apiKey.isEmpty()) {
            throw new IllegalStateException("API Key is missing");
        }
    }

    @Test
    public void testConvertTemperatureToCelsius() {
        // Test to convert temperature to Celsius
        double fahrenheit = 98.6;
        double celsius = weatherService.convertTemperature(fahrenheit, true);
        assert(celsius == 37); // Check if the conversion is correct
    }

    @Test
    public void testConvertTemperatureToFahrenheit() {
        // Test to convert temperature to Fahrenheit
        double celsius = 37;
        double fahrenheit = weatherService.convertTemperature(celsius, false);
        assert(fahrenheit == 98.6); // Check if the conversion is correct
    }

    @Test
    public void testFetchWeatherData() throws Exception {
        // Test for fetching weather data (mocking HTTP requests can be done here)
        weatherService.fetchWeatherData();
        // Add assertions based on what you expect to happen
    }
}
