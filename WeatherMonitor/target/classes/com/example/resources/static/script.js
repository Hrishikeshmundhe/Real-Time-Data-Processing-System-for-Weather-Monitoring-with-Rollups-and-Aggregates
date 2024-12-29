document.getElementById('weatherForm').addEventListener('submit', async function (e) {
    e.preventDefault();

    const city = document.getElementById('city').value;

    // Clear previous weather data
    document.getElementById('weatherData').innerHTML = '';

    try {
        // Fetch weather data from your API
        const response = await fetch(`/weather/summaries/${city}`);

        if (!response.ok) {
            throw new Error('Weather data not found');
        }

        const weatherData = await response.json();

        // Display the weather data
        const weatherDiv = document.getElementById('weatherData');
        const cityData = weatherData[0]; // Assuming only one record per city

        weatherDiv.innerHTML = `
            <h3>Weather Summary for ${cityData.city} on ${cityData.date}</h3>
            <p>Average Temperature: ${cityData.avgTemperature}°C</p>
            <p>Max Temperature: ${cityData.maxTemperature}°C</p>
            <p>Min Temperature: ${cityData.minTemperature}°C</p>
            <p>Dominant Condition: ${cityData.dominantCondition}</p>
        `;
    } catch (error) {
        // Display error message if the API call fails
        document.getElementById('weatherData').innerHTML = `<p class="error">${error.message}</p>`;
    }
});
