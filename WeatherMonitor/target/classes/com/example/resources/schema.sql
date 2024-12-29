CREATE SCHEMA IF NOT EXISTS WeatherMonitoring;

CREATE TABLE IF NOT EXISTS daily_weather_summary (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,  -- Use BIGINT with AUTO_INCREMENT in H2
    city VARCHAR(255) NOT NULL,
    date DATE NOT NULL,
    avg_temp DOUBLE NOT NULL,
    max_temp DOUBLE NOT NULL,
    min_temp DOUBLE NOT NULL,
    dominant_condition VARCHAR(255) NOT NULL,
    timestamp TIMESTAMP NOT NULL,  -- Use TIMESTAMP for Instant
    CONSTRAINT unique_city_date UNIQUE (city, date)
);

