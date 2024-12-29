package com.example.weather;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.database.DailyWeatherSummary;
import com.example.database.DatabaseService;
import org.json.JSONObject;
import com.example.weather.WeatherAlert;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.Instant;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Service
public class WeatherService {
    private static final String[] CITIES = {"Delhi", "Mumbai", "Chennai", "Bangalore", "Kolkata", "Hyderabad"};
    
    private String apiKey = "575ca8ce4fe001b286f51403628ed8cd";  // Replace with actual API key
    private String baseUrl = "https://api.openweathermap.org/data/2.5/weather";  // Define base URL here

    private long interval = 60000;  // Set to 1 minute (60,000 milliseconds)

    private final WeatherAlert weatherAlert;
    private final Map<String, List<WeatherData>> dailyWeatherData = new HashMap<>();

    private final DatabaseService databaseService;

    @Autowired
    public WeatherService(WeatherAlert weatherAlert, DatabaseService databaseService) {
        this.weatherAlert = weatherAlert;
        this.databaseService = databaseService;
    }

    // Builds the URL for the weather API request
    public String buildApiUrl(String city) {
        return String.format("%s?q=%s&appid=%s&units=metric", baseUrl, city, apiKey);
    }

    // Fetches the weather data for all cities
    public void fetchWeatherData() {
        for (String city : CITIES) {
            try {
                String urlStr = buildApiUrl(city);
                HttpURLConnection conn = (HttpURLConnection) new URL(urlStr).openConnection();
                conn.setRequestMethod("GET");

                handleHttpConnection(conn, city);
            } catch (Exception e) {
                System.err.println("Error fetching data for city: " + city + " - " + e.getMessage());
            }
        }
        processAndSaveDailySummary();
    }

    // Handles HTTP connection and parses response
    private void handleHttpConnection(HttpURLConnection conn, String city) throws Exception {
        int responseCode = conn.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            try (BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
                StringBuilder response = new StringBuilder();
                String inputLine;

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }

                parseWeatherResponse(response.toString(), city);
            } finally {
                conn.disconnect();  // Ensure the connection is closed
            }
        } else {
            System.err.println("GET request failed for city: " + city + " with response code: " + responseCode);
        }
    }

    // Parses the weather data from the response JSON
    private void parseWeatherResponse(String response, String city) {
        JSONObject jsonObj = new JSONObject(response);
        JSONObject main = jsonObj.getJSONObject("main");

        double temperature = main.getDouble("temp");
        double feelsLike = main.getDouble("feels_like");
        int humidity = main.getInt("humidity");
        String description = jsonObj.getJSONArray("weather").getJSONObject(0).getString("description");
        long timestamp = jsonObj.getLong("dt");

        WeatherData weatherData = new WeatherData(temperature, feelsLike, humidity, description, timestamp);
        dailyWeatherData.computeIfAbsent(city, k -> new ArrayList<>()).add(weatherData);

        weatherAlert.checkTemperatureAlert(temperature, city);
        weatherAlert.checkWeatherConditionAlert(description, city);
    }

       // Processes the weather data and saves the daily summary to the database
       public void processAndSaveDailySummary() {
        for (String city : CITIES) {
            List<WeatherData> data = dailyWeatherData.get(city);
            if (data != null && !data.isEmpty()) {
                double sumTemp = 0, maxTemp = Double.MIN_VALUE, minTemp = Double.MAX_VALUE;
                Map<String, Integer> conditionCount = new HashMap<>();

                for (WeatherData entry : data) {
                    sumTemp += entry.getTemperature();
                    maxTemp = Math.max(maxTemp, entry.getTemperature());
                    minTemp = Math.min(minTemp, entry.getTemperature());
                    conditionCount.put(entry.getCondition(), conditionCount.getOrDefault(entry.getCondition(), 0) + 1);
                }

                double avgTemp = sumTemp / data.size();
                String dominantCondition = Collections.max(conditionCount.entrySet(), Map.Entry.comparingByValue()).getKey();

                // Check if a summary for this city and date already exists
                DailyWeatherSummary existingSummary = databaseService.findSummaryByCityAndDate(city, LocalDate.now());
                if (existingSummary != null) {
                    // Update the existing summary
                    existingSummary.setAvgTemperature(avgTemp);
                    existingSummary.setMaxTemperature(maxTemp);
                    existingSummary.setMinTemperature(minTemp);
                    existingSummary.setDominantCondition(dominantCondition);
                    existingSummary.setTimestamp(Instant.now());
                    databaseService.saveDailySummary(existingSummary);
                } else {
                    // Create a new summary
                    DailyWeatherSummary newSummary = new DailyWeatherSummary(city, LocalDate.now(), avgTemp, maxTemp, minTemp, dominantCondition, Instant.now());
                    databaseService.saveDailySummary(newSummary);
                }
            }
        }
    }


    // Starts the weather monitoring with periodic data fetching
    public void startWeatherMonitoring() {
        databaseService.createTableIfNotExists();
        if (interval <= 0) {
            throw new IllegalArgumentException("Interval must be a positive value.");
        }
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
        executor.scheduleAtFixedRate(this::fetchWeatherData, 0, interval, TimeUnit.MILLISECONDS);
    }

    // Converts temperature between Celsius and Fahrenheit
    public double convertTemperature(double temperature, boolean toCelsius) {
        return toCelsius ? (temperature - 32) * 5 / 9 : (temperature * 9 / 5) + 32;
    }

    // Inner class to represent weather data
    private static class WeatherData {
        private final double temperature;
        private final double feelsLike;
        private final int humidity;
        private final String condition;
        private final long timestamp;

        public WeatherData(double temperature, double feelsLike, int humidity, String condition, long timestamp) {
            this.temperature = temperature;
            this.feelsLike = feelsLike;
            this.humidity = humidity;
            this.condition = condition;
            this.timestamp = timestamp;
        }

        public double getTemperature() {
            return temperature;
        }

        public String getCondition() {
            return condition;
        }
    }
}
