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
import br.ufpe.cin.android.rainmember.data.WeatherDataUpdater

class WeatherDataWorker (context: Context, workerParams: WorkerParameters) : Worker(context, workerParams) {
    companion object {
        const val TAG = "WeatherDataWorker"
    }

    private val weatherApi: WeatherApi = WeatherDataUpdater()

    override fun doWork(): Result {
        Log.d(TAG, "Fetching data for:")

        val locationManager = applicationContext.getSystemService(Context.LOCATION_SERVICE) as LocationManager

        if (ContextCompat.checkSelfPermission(applicationContext, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            val location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)

            Log.d (TAG, "Lat: ${location.latitude}")
            Log.d (TAG, "Lon: ${location.longitude}")

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
