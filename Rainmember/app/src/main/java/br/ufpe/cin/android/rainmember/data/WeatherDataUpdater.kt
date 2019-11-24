package br.ufpe.cin.android.rainmember.data

class WeatherDataUpdater : WeatherApi {

    private val weatherApi : WeatherApi = OpenWeatherApi("")
    private val uvApi : WeatherApi = OpenUVApi("")


    override fun getWeatherForecast(latitude: Double, longitude: Double): List<WeatherData> {

        return weatherApi.getWeatherForecast(latitude, longitude)
    }

    override fun getCurrentWeather(latitude: Double, longitude: Double): WeatherData {
        val weatherData = weatherApi.getCurrentWeather(latitude, longitude)
        val uvData = uvApi.getCurrentWeather(latitude, longitude)

        return WeatherData(
            cityName = weatherData.cityName,
            temperature = weatherData.temperature,
            maxTemperature = weatherData.maxTemperature,
            minTemperature = weatherData.minTemperature,
            humidity = weatherData.humidity,
            condition = weatherData.condition,
            weatherCode = weatherData.weatherCode,
            datetime = weatherData.datetime,
            currentUv = uvData.currentUv,
            maxUv = uvData.maxUv
        )
    }
}