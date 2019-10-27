package br.ufpe.cin.android.rainmember.data

import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject

private const val API_URL = "http://api.openweathermap.org/data/2.5/"

class OpenWeatherApi (private val appId: String) : WeatherApi {

    private val client = OkHttpClient()

    override fun getWeatherForecast(latitude: Long, longitude: Long): List<WeatherData> {
        val result = arrayListOf<WeatherData>()

        val request = Request.Builder()
            .url("${API_URL}/forecast?APPID=${appId}&lat=${latitude}&long=${longitude}")
            .build()

        client.newCall(request).execute().use { response ->
            val body = JSONObject(response.body!!.string())

            val forecastList = body.getJSONArray("list")

            for (i in 0 until forecastList.length() ) {
                result.add(jsonToWeatherData(forecastList.getJSONObject(i)))
            }
        }

        return result
    }

    override fun getCurrentWeather(latitude: Long, longitude: Long): WeatherData {
        val request = Request.Builder()
            .url("${API_URL}/weather?APPID=${appId}&lat=${latitude}&long=${longitude}")
            .build()

        client.newCall(request).execute().use { response ->
            val body = JSONObject(response.body!!.string())

            return jsonToWeatherData(body)
        }
    }

    private fun jsonToWeatherData(json: JSONObject): WeatherData {
        val weatherInfo = json.getJSONObject("weather")
        val condition = weatherInfo.getString("description")

        val mainData = json.getJSONObject("main")
        val temperature = mainData.getDouble("temp")
        val humidity = mainData.getDouble("humidity")
        val maximumTemperature = mainData.getDouble("temp_max")
        val minimumTemperature = mainData.getDouble("temp_min")

        return WeatherData(
            temperature = temperature,
            maxTemperature = maximumTemperature, minTemperature = minimumTemperature,
            humidity = humidity,
            condition = condition
        )
    }

}