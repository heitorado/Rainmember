package br.ufpe.cin.android.rainmember.data

import android.util.Log
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject

private const val API_URL = "https://api.openweathermap.org/data/2.5/"

class OpenWeatherApi (private val appId: String) : WeatherApi {

    private val client = OkHttpClient()

    override fun getWeatherForecast(latitude: Double, longitude: Double): List<WeatherData> {
        val result = arrayListOf<WeatherData>()

        val request = Request.Builder()
            .url("${API_URL}/forecast?${queryParams(latitude, longitude)}")
            .build()

        client.newCall(request).execute().use { response ->
            val responseString = response.body!!.string()

            Log.d ("OpenWeatherApi", responseString )
            val body = JSONObject(responseString)

            val forecastList = body.getJSONArray("list")

            for (i in 0 until forecastList.length() ) {
                result.add(jsonToWeatherData(forecastList.getJSONObject(i)))
            }
        }

        return result
    }

    override fun getCurrentWeather(latitude: Double, longitude: Double): WeatherData {
        val request = Request.Builder()
            .url("${API_URL}/weather?${queryParams(latitude, longitude)}")
            .build()

        client.newCall(request).execute().use { response ->
            val responseString = response.body!!.string()
            Log.d ("OpenWeatherApi", responseString )
            val body = JSONObject(responseString)

            return jsonToWeatherData(body)
        }
    }

    private fun queryParams (latitude: Double, longitude: Double): String =
        "APPID=${appId}&lat=${latitude}&lon=${longitude}"


    private fun jsonToWeatherData(json: JSONObject): WeatherData {
        val weatherInfo = json.getJSONArray("weather").get(0) as JSONObject

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