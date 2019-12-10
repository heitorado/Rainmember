package br.ufpe.cin.android.rainmember.data

import android.util.Log
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject
import java.util.*

private const val API_URL = "https://api.openuv.io/api/v1/uv"

class OpenUVApi (private val apiKey: String) : WeatherApi {

    private val client = OkHttpClient()

    override fun getWeatherForecast(latitude: Double, longitude: Double): List<WeatherData> {
        return ArrayList(0)
    }

    override fun getCurrentWeather(latitude: Double, longitude: Double): WeatherData {
        val request = Request.Builder()
            .url("${API_URL}?${queryParams(latitude, longitude)}")
            .header("x-access-token", apiKey)
            .build()

        client.newCall(request).execute().use { response ->
            val responseString = response.body!!.string()
            Log.d ("OpenUVApi", responseString )
            val body = JSONObject(responseString)

            return jsonToWeatherData(body)
        }
    }

    private fun queryParams (latitude: Double, longitude: Double): String =
        "lat=${latitude}&lng=${longitude}"


    private fun jsonToWeatherData(json: JSONObject): WeatherData {
        val result = json.getJSONObject("result")
        val currentUv = result.getDouble("uv")
        val maxUv = result.getDouble("uv_max")


        return WeatherData(
            cityName = "",
            temperature = 0.0,
            maxTemperature = 0.0,
            minTemperature = 0.0,
            humidity = 0.0,
            condition = "",
            weatherCode = 0,
            datetime = Date(),
            currentUv = currentUv,
            maxUv = maxUv
        )
    }

}