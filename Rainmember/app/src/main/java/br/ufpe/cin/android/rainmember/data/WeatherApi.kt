package br.ufpe.cin.android.rainmember.data

interface WeatherApi {

    fun getCurrentWeather(latitude: Double, longitude: Double, kind : String) : WeatherData

    fun getWeatherForecast (latitude: Double, longitude: Double) : List<WeatherData>

    fun getWeatherByCityId (city_id : String, kind : String ) : WeatherData?
}