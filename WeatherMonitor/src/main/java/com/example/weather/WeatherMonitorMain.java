package com.example.weather;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import com.example.database.DatabaseService;
import com.example.database.DailyWeatherSummary;
import com.example.database.DailyWeatherSummaryService;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONObject;
import java.time.Instant;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SpringBootApplication(scanBasePackages = {"com.example.weather", "com.example.database"})
@ComponentScan(basePackages = {"com.example.weather", "com.example.database"})
@EnableJpaRepositories(basePackages = {"com.example.database"})
@EntityScan(basePackages = {"com.example.database"})
@EnableConfigurationProperties(WeatherApiConfig.class)
public class WeatherMonitorMain {

    public static void main(String[] args) {
        SpringApplication.run(WeatherMonitorMain.class, args);
    }
}

@Component
class WeatherMonitorService implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(WeatherMonitorService.class);

   
    private String apiKey = "575ca8ce4fe001b286f51403628ed8cd";


     
    private long interval = 60000;  // Set to 1 minute (60,000 milliseconds)


    private static final String[] CITIES = {"Delhi", "Mumbai", "Chennai", "Bangalore", "Kolkata", "Hyderabad"};
    private static final String BASE_URL = "https://api.openweathermap.org/data/2.5/weather";

    private final Map<String, List<WeatherData>> dailyWeatherData = new ConcurrentHashMap<>();

    @Autowired
    private DatabaseService databaseService;

    @Autowired
    private DailyWeatherSummaryService dailyWeatherSummaryService;

    @Autowired
    private WeatherService weatherService;

    @Override
    public void run(String... args) {
        logger.info("Starting weather monitoring...");
        weatherService.startWeatherMonitoring();  // Start weather monitoring from WeatherService
    }

    @Scheduled(fixedRateString = "${weather.api.interval}")
    public void fetchWeatherDataScheduled() {
        for (String city : CITIES) {
            fetchAndDisplayWeatherData(city);
        }
    }

    @Scheduled(cron = "0 0 0 * * *") // Runs at midnight
    public void dailySummaryScheduled() {
        displayDailySummary();
    }

    private void fetchAndDisplayWeatherData(String city) {
        try {
            String apiUrl = String.format("%s?q=%s&appid=%s&units=metric", BASE_URL, city, apiKey);
            HttpURLConnection connection = (HttpURLConnection) new URL(apiUrl).openConnection();
            connection.setRequestMethod("GET");

            if (connection.getResponseCode() != 200) {
                logger.error("Failed to fetch weather data for city: {} - HTTP Status: {}", city, connection.getResponseCode());
                return;
            }

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();

            parseWeatherData(response.toString(), city);

        } catch (Exception e) {
            logger.error("Error fetching weather data for city: {}", city, e);
        }
    }

    private void parseWeatherData(String jsonData, String city) {
        JSONObject jsonObject = new JSONObject(jsonData);
        JSONObject main = jsonObject.getJSONObject("main");

        double temperature = main.getDouble("temp");
        double feelsLike = main.getDouble("feels_like");
        double humidity = main.getDouble("humidity");
        String weatherDescription = jsonObject.getJSONArray("weather").getJSONObject(0).getString("description");

        logger.info("City: {}, Temperature: {} °C, Feels Like: {} °C, Humidity: {} %, Description: {}", 
                city, temperature, feelsLike, humidity, weatherDescription);

        WeatherData weatherData = new WeatherData(temperature, weatherDescription, Instant.now().toEpochMilli());

        dailyWeatherData.putIfAbsent(city, new ArrayList<>());
        dailyWeatherData.get(city).add(weatherData);
    }

    private void displayDailySummary() {
        for (String city : CITIES) {
            List<WeatherData> data = dailyWeatherData.get(city);
            if (data != null && !data.isEmpty()) {
                double sumTemp = 0, maxTemp = Double.MIN_VALUE, minTemp = Double.MAX_VALUE;
                Map<String, Integer> conditionCount = new HashMap<>();

                for (WeatherData entry : data) {
                    sumTemp += entry.temperature;
                    if (entry.temperature > maxTemp) maxTemp = entry.temperature;
                    if (entry.temperature < minTemp) minTemp = entry.temperature;

                    conditionCount.put(entry.condition, conditionCount.getOrDefault(entry.condition, 0) + 1);
                }

                double avgTemp = sumTemp / data.size();
                String dominantCondition = Collections.max(conditionCount.entrySet(), Map.Entry.comparingByValue()).getKey();

                logger.info("Daily Summary for City: {}, Average Temperature: {} °C, Max Temperature: {} °C, Min Temperature: {} °C, Dominant Condition: {}", 
                        city, avgTemp, maxTemp, minTemp, dominantCondition);

                DailyWeatherSummary summary = new DailyWeatherSummary();
                summary.setCity(city);
                summary.setDate(LocalDate.now());
                summary.setAvgTemperature(avgTemp);
                summary.setMaxTemperature(maxTemp);
                summary.setMinTemperature(minTemp);
                summary.setDominantCondition(dominantCondition);
                summary.setTimestamp(Instant.ofEpochMilli(System.currentTimeMillis()));  // Set current timestamp

                dailyWeatherSummaryService.saveDailyWeatherSummary(summary);

                dailyWeatherData.get(city).clear();
            }
        }
    }

    private static class WeatherData {
        double temperature;
        String condition;
        long timestamp;

        WeatherData(double temperature, String condition, long timestamp) {
            this.temperature = temperature;
            this.condition = condition;
            this.timestamp = timestamp;
        }

        public long getTimestamp() {
            return timestamp;
        }
    }
}
