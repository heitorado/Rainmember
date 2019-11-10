package br.ufpe.cin.android.rainmember.data

interface WeatherApi {

    fun getCurrentWeather(latitude: Double, longitude: Double) : WeatherData

    fun getWeatherForecast (latitude: Double, longitude: Double) : List<WeatherData>
}