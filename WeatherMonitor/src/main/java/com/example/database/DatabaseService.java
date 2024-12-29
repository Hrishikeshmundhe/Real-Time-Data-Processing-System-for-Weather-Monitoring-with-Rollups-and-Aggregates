package com.example.database;

import com.example.database.DailyWeatherSummary;
import com.example.database.DailyWeatherSummaryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class DatabaseService {

    private static final Logger logger = LoggerFactory.getLogger(DatabaseService.class);

    private final DailyWeatherSummaryRepository summaryRepository;

    @Autowired
    public DatabaseService(DailyWeatherSummaryRepository summaryRepository) {
        this.summaryRepository = summaryRepository;
    }

    // Save a DailyWeatherSummary to the database
    public void saveDailySummary(DailyWeatherSummary summary) {
        try {
            summaryRepository.save(summary);
            logger.info("Daily weather summary saved for city: {}", summary.getCity());
        } catch (DataAccessException e) {
            logger.error("Error saving daily weather summary for city: {}", summary.getCity(), e);
        }
    }

    // Retrieve summaries for a specific city
    public List<DailyWeatherSummary> getSummariesForCity(String city) {
        try {
            List<DailyWeatherSummary> summaries = summaryRepository.findByCity(city);
            logger.info("Retrieved {} summaries for city: {}", summaries.size(), city);
            return summaries != null ? summaries : new ArrayList<>();
        } catch (DataAccessException e) {
            logger.error("Error retrieving summaries for city: {}", city, e);
            return new ArrayList<>();
        }
    }

    // Find a summary for a specific city and date
    public DailyWeatherSummary findSummaryByCityAndDate(String city, LocalDate date) {
        try {
            DailyWeatherSummary summary = summaryRepository.findByCityAndDate(city, date);
            if (summary != null) {
                logger.info("Found summary for city: {} and date: {}", city, date);
            } else {
                logger.info("No summary found for city: {} and date: {}", city, date);
            }
            return summary;
        } catch (DataAccessException e) {
            logger.error("Error retrieving summary for city: {} and date: {}", city, date, e);
            return null;
        }
    }

    // Create table if it does not exist (placeholder)
    public void createTableIfNotExists() {
        // Spring Boot's auto-configuration should handle this by default
        // This method can be used if you're doing custom table creation or handling schema migrations
        logger.info("Checking if the table exists (this is a placeholder method).");
    }
}
