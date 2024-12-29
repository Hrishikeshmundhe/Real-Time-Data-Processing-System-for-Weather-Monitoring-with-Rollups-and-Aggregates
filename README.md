Weather Monitoring System

1)Overview-
  This project implements a real-time weather monitoring system that tracks weather conditions across multiple cities using the OpenWeatherMap API. It provides features like real-time weather data retrieval, daily weather summaries, temperature alerts, and more, enabling users to monitor weather conditions and receive timely updates.

2)Key Features-
  - Real-time Weather Data: Fetch up-to-date weather information for multiple cities.
  - Daily Weather Summaries: Aggregate and store daily weather data, including high, low, and average temperatures.
    - Custom Alerts: Get notified when temperature thresholds are exceeded, with optional email or console alerts.
    - Database Storage: Store historical weather data in an H2 database for long-term analysis.
    - Scheduled Data Fetching: Fetch weather data at configurable intervals.
    - Weather Monitoring UI: Display weather summaries and alerts on a web-based interface.


3)Design Approach-
  - Modular System: The application is divided into multiple components, such as data retrieval, aggregation, alerting, and storage, ensuring scalability and ease of maintenance.
  - Asynchronous Data Fetching: The application uses Spring Boot’s scheduled tasks to fetch weather data for multiple cities concurrently.
  - Database Integration: Weather data is stored in an H2 database for aggregation and easy retrieval of historical data.
  - Configurable Alerts: Temperature thresholds are configurable, allowing users to customize when to be notified based on specific conditions.
  - User Interface: The web interface built using HTML, CSS, and JavaScript provides users with a simple way to enter cities and view weather data.


4) Prerequisites-
  - Java 11 or newer (for Spring Boot application)
  - Maven (for building the project)
  - H2 Database (used for storing weather data)
  - Spring Boot dependencies (configured via `pom.xml`)


5) Setup Instructions-
  -Clone the Repository 
  -git clone https://github.com/Hrishikeshmundhe/Real-Time-Data-Processing-System-for-Weather-Monitoring-with-Rollups-and-Aggregates.git
  -cd Weather-Monitoring-System


6)Build and Run the Application
   1)Open your terminal or command prompt in the project folder.
   2)Build the project:
     -mvn clean install
   3)Run the application:
     -mvn spring-boot:run


7)Configuring the Application
 -API Key: OpenWeatherMap API key is required to fetch weather data. You can obtain it from OpenWeatherMap.
 -Configuration Settings: You can modify the settings such as cities to track, update intervals, and alert thresholds within the application.properties file located in src/main/resources.
 -Example configuration options:
   weather.cities: A comma-separated list of city names to monitor (e.g., Mumbai,Delhi,Kolkata).
   weather.updateInterval: The time interval (in seconds) between data fetches.
   weather.alertThreshold: Temperature values (in Celsius) that will trigger alerts when exceeded.


8)Run the Application
  -Once the project is set up and configured, you can run the application by executing the following command:
      mvn spring-boot:run


9)Access the Web Interface
  -The application will be available at:
      http://localhost:8080
  -You can enter a city name in the input field and click "Get Weather" to view the real-time weather data.



10)Configuration Options
  -OpenWeatherMap API Key: Add your OpenWeatherMap API key to application.properties.
  -Cities to Monitor: A list of city names to monitor (can be configured in the properties file).
  -Update Interval: The interval (in seconds) between weather data retrievals.
  -Alert Thresholds: The temperature threshold that triggers alerts.


11)Running Tests
  -To execute the test suite:
    mvn test


12)Extending the System
  -Add More Cities: Add more cities to the cities list in the application.properties file.
  -Change Alert Settings: Modify the alertThreshold in the properties file to set custom temperature thresholds.
  -Enhance UI: Modify the web interface in src/main/resources/static to add new features like graphs or additional data points.


13)Troubleshooting
  -API Key Issues: Ensure that the OpenWeatherMap API key is correctly configured in the application.properties file.
  -No Data: Check your internet connection and ensure that the OpenWeatherMap API is accessible.
  -UI Display Issues: Verify that the front-end files are correctly placed in the src/main/resources/static folder and the correct port is being used for accessing the application.