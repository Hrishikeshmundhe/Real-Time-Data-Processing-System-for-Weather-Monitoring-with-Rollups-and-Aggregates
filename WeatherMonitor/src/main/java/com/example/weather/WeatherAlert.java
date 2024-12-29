package com.example.weather;
import com.example.weather.WeatherAlert;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


@Component
public class WeatherAlert {

    private static final Logger logger = LoggerFactory.getLogger(WeatherAlert.class);

    private double tempThresholdHigh = 35.0; // default threshold
    private double tempThresholdLow = 5.0;  // default threshold
    private String conditionThreshold = "storm"; // default condition

    // Setters to configure thresholds
    public void setTempThresholdHigh(double threshold) {
        this.tempThresholdHigh = threshold;
    }

    public void setTempThresholdLow(double threshold) {
        this.tempThresholdLow = threshold;
    }

    public void setConditionThreshold(String condition) {
        this.conditionThreshold = condition;
    }

    // Check temperature thresholds and generate an alert if needed
    // The method signature already expects 'temperature' and 'city' as parameters, which is correct.
    public void checkTemperatureAlert(double temperature, String city) {
        if (temperature > tempThresholdHigh) {
            String alertMessage = String.format("ALERT: High temperature detected in %s: %.2f°C (Threshold: %.2f°C)", city, temperature, tempThresholdHigh);
            System.out.println(alertMessage);
            logger.warn(alertMessage);  // Log the alert
        } else if (temperature < tempThresholdLow) {
            String alertMessage = String.format("ALERT: Low temperature detected in %s: %.2f°C (Threshold: %.2f°C)", city, temperature, tempThresholdLow);
            System.out.println(alertMessage);
            logger.warn(alertMessage);  // Log the alert
        }
    }

    // Check weather condition thresholds and generate an alert if needed
    // The method expects 'condition' and 'city' as parameters, so no change is required here.
    public void checkWeatherConditionAlert(String condition, String city) {
        if (condition.equalsIgnoreCase(conditionThreshold)) {
            String alertMessage = String.format("ALERT: Severe weather condition detected in %s: %s", city, condition);
            System.out.println(alertMessage);
            logger.warn(alertMessage);  // Log the alert
        }
    }
}
