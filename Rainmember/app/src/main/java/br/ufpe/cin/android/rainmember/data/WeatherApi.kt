package br.ufpe.cin.android.rainmember.data

interface WeatherApi {

    fun getCurrentWeather(latitude: Long, longitude: Long) : WeatherData

    fun getWeatherForecast (latitude: Long, longitude: Long) : List<WeatherData>
}