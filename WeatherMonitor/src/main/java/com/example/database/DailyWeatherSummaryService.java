package com.example.database;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("dailyWeatherSummaryService")  // Optional: Name the service to avoid naming conflicts
public class DailyWeatherSummaryService {

    private final DailyWeatherSummaryRepository repository;

    @Autowired
    public DailyWeatherSummaryService(DailyWeatherSummaryRepository repository) {
        this.repository = repository;
    }

    // Business logic methods related to DailyWeatherSummary
    public void saveDailyWeatherSummary(DailyWeatherSummary summary) {
        repository.save(summary);  // Save to the database
    }

    public DailyWeatherSummary getDailyWeatherSummary(Long id) {
        return repository.findById(id).orElse(null);  // Fetch summary by ID
    }

    // Add more methods for querying or processing DailyWeatherSummary as needed
}
