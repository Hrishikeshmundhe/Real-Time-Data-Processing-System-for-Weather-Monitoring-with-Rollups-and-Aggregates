package com.example.database;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface DailyWeatherSummaryRepository extends JpaRepository<DailyWeatherSummary, Long> {
    
    // This method is automatically implemented by Spring Data JPA
    List<DailyWeatherSummary> findByCity(String city);

    // Custom method to find a summary by city and date
    DailyWeatherSummary findByCityAndDate(String city, LocalDate date);
}
