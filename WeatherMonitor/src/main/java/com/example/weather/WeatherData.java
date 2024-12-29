import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class WeatherData {

    private double temperature;
    private String condition;
    private long timestamp;
    WeatherData weatherData = new WeatherData(temperature, condition, timestamp);

    // Constructor to initialize temperature, condition, and timestamp
    public WeatherData(double temperature, String condition, long timestamp) {
        this.temperature = temperature;
        this.condition = condition;
        this.timestamp = timestamp;
    }

    // Getters and setters
    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    // Optional: Convert temperature from Kelvin to Celsius
    public double kelvinToCelsius() {
        return this.temperature - 273.15;
    }
}
