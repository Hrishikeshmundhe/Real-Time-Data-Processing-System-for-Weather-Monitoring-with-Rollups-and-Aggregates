package com.example.weather;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.database.DailyWeatherSummary;
import com.example.database.DailyWeatherSummaryRepository;

import java.util.List;

@Service("weatherSummaryService") 
public class WeatherSummaryService {

    private final DailyWeatherSummaryRepository summaryRepository;

    @Autowired
    public WeatherSummaryService(DailyWeatherSummaryRepository summaryRepository) {
        this.summaryRepository = summaryRepository;
    }

    public void saveDailySummary(DailyWeatherSummary summary) {
        summaryRepository.save(summary);
    }

    public List<DailyWeatherSummary> getSummariesForCity(String city) {
        return summaryRepository.findByCity(city);
    }
}
