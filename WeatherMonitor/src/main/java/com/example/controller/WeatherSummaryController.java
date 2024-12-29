package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.database.DailyWeatherSummary;
import com.example.database.DatabaseService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/weather")
public class WeatherSummaryController {

    private final DatabaseService databaseService;

    @Autowired
    public WeatherSummaryController(DatabaseService databaseService) {
        this.databaseService = databaseService;
    }

    // Save daily weather summary
    @PostMapping("/summary")
    public ResponseEntity<Void> saveDailySummary(@Valid @RequestBody DailyWeatherSummary summary) {
        databaseService.saveDailySummary(summary); // Assuming DatabaseService handles persistence
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    // Retrieve summaries for a specific city
    @GetMapping("/summaries/{city}")
    public ResponseEntity<List<DailyWeatherSummary>> getSummariesForCity(@PathVariable String city) {
        List<DailyWeatherSummary> summaries = databaseService.getSummariesForCity(city);
        if (summaries.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // No data found
        }
        return ResponseEntity.ok(summaries);
    }
}
