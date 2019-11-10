package br.ufpe.cin.android.rainmember.br.ufpe.cin.android.rainmember.data

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.LocationManager
import android.util.Log
import androidx.core.content.ContextCompat
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.work.Worker
import androidx.work.WorkerParameters
import br.ufpe.cin.android.rainmember.R
import br.ufpe.cin.android.rainmember.br.ufpe.cin.android.rainmember.data.room.WeatherDataDB
import br.ufpe.cin.android.rainmember.data.OpenWeatherApi
import br.ufpe.cin.android.rainmember.data.WeatherApi

class WeatherDataWorker (context: Context, workerParams: WorkerParameters) : Worker(context, workerParams) {
    companion object {
        const val TAG = "WeatherDataWorker"
        const val UPDATE_WEATHER_DATA_BROADCAST = "update_weather_data_broadcast"
    }

    private val weatherApi: WeatherApi = OpenWeatherApi("")

    override fun doWork(): Result {
        Log.d(TAG, "Fetching data")

        val locationManager = applicationContext.getSystemService(Context.LOCATION_SERVICE) as LocationManager

        if (ContextCompat.checkSelfPermission(applicationContext, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            val location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)

            Log.d (TAG, location.latitude.toString())
            Log.d (TAG, location.longitude.toString())

            val data = weatherApi.getCurrentWeather(location.latitude, location.longitude)

            val db = WeatherDataDB.getDatabase(applicationContext)

            db.weatherDataDAO().addWeatherData(data)

            LocalBroadcastManager.getInstance(applicationContext).sendBroadcast(
                Intent(applicationContext.getString(R.string.weather_data_change))
            )
        }

        return Result.success()
    }
}
