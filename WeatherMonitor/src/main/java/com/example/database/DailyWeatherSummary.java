package com.example.database;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import jakarta.persistence.UniqueConstraint;
import java.time.LocalDate;
import java.time.Instant;

@Entity
@Table(
    name = "daily_weather_summary",
    uniqueConstraints = @UniqueConstraint(columnNames = {"city", "date"})
)
public class DailyWeatherSummary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "city", length = 100, nullable = false)
    private String city;

    @Column(name = "date", nullable = false)
    private LocalDate date;

    @Column(name = "avg_temperature", nullable = false)
    private Double avgTemperature;

    @Column(name = "max_temperature", nullable = false)
    private Double maxTemperature;

    @Column(name = "min_temperature", nullable = false)
    private Double minTemperature;

    @Column(name = "dominant_condition", length = 100, nullable = false)
    private String dominantCondition;

    @Column(name = "timestamp", nullable = false)
    private Instant timestamp;

    // Default constructor
    public DailyWeatherSummary() {
    }

    // Parameterized constructor to create an object with specific fields
    public DailyWeatherSummary(String city, LocalDate date, Double avgTemperature, 
                                Double maxTemperature, Double minTemperature, 
                                String dominantCondition, Instant timestamp) {
        this.city = city;
        this.date = date;
        this.avgTemperature = avgTemperature;
        this.maxTemperature = maxTemperature;
        this.minTemperature = minTemperature;
        this.dominantCondition = dominantCondition;
        this.timestamp = timestamp;
    }

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Double getAvgTemperature() {
        return avgTemperature;
    }

    public void setAvgTemperature(Double avgTemperature) {
        this.avgTemperature = avgTemperature;
    }

    public Double getMaxTemperature() {
        return maxTemperature;
    }

    public void setMaxTemperature(Double maxTemperature) {
        this.maxTemperature = maxTemperature;
    }

    public Double getMinTemperature() {
        return minTemperature;
    }

    public void setMinTemperature(Double minTemperature) {
        this.minTemperature = minTemperature;
    }

    public String getDominantCondition() {
        return dominantCondition;
    }

    public void setDominantCondition(String dominantCondition) {
        this.dominantCondition = dominantCondition;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
    }
}
