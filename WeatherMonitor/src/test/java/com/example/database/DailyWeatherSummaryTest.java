package com.example.database;

import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class DailyWeatherSummaryTest {

    @Test
    public void testDailyWeatherSummarySettersAndGetters() {
        // Create an instance of DailyWeatherSummary
        DailyWeatherSummary summary = new DailyWeatherSummary();

        // Test setting values
        summary.setCity("New York");
        summary.setDate(LocalDate.of(2024, 12, 17));
        summary.setAvgTemperature(25.5);
        summary.setMaxTemperature(30.0);
        summary.setMinTemperature(20.0);
        summary.setDominantCondition("Sunny");
        summary.setTimestamp(Instant.now());

        // Verify values
        assertEquals("New York", summary.getCity());
        assertEquals(LocalDate.of(2024, 12, 17), summary.getDate());
        assertEquals(25.5, summary.getAvgTemperature());
        assertEquals(30.0, summary.getMaxTemperature());
        assertEquals(20.0, summary.getMinTemperature());
        assertEquals("Sunny", summary.getDominantCondition());
        assertNotNull(summary.getTimestamp());
    }

    @Test
    public void testDefaultConstructor() {
        // Create an instance using the default constructor
        DailyWeatherSummary summary = new DailyWeatherSummary();

        // Ensure fields are null or default values
        assertNotNull(summary);
        assertEquals(null, summary.getCity());
        assertEquals(null, summary.getDate());
        assertEquals(null, summary.getAvgTemperature());
        assertEquals(null, summary.getMaxTemperature());
        assertEquals(null, summary.getMinTemperature());
        assertEquals(null, summary.getDominantCondition());
        assertEquals(null, summary.getTimestamp());
    }
}
