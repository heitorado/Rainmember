package br.ufpe.cin.android.rainmember.br.ufpe.cin.android.rainmember.data

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.util.Log
import androidx.core.content.ContextCompat
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.preference.PreferenceManager
import androidx.work.Worker
import androidx.work.WorkerParameters
import br.ufpe.cin.android.rainmember.R
import br.ufpe.cin.android.rainmember.br.ufpe.cin.android.rainmember.data.room.WeatherDataDB
import br.ufpe.cin.android.rainmember.data.WeatherApi
import br.ufpe.cin.android.rainmember.data.WeatherDataUpdater
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.File

class WeatherDataWorker (context: Context, workerParams: WorkerParameters) : Worker(context, workerParams) {
    companion object {
        const val TAG = "WeatherDataWorker"
    }

    private val weatherApi: WeatherApi = WeatherDataUpdater()
    private val ctx = context
    private val client = OkHttpClient()

    override fun doWork(): Result {

        val locationManager = applicationContext.getSystemService(Context.LOCATION_SERVICE) as LocationManager

        if (ContextCompat.checkSelfPermission(applicationContext, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            val location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
            val sharedPref = PreferenceManager.getDefaultSharedPreferences(applicationContext)

            fetchWeatherDataForCurrentLocation(location)

            fetchWeatherDataForComparisonLocation(sharedPref.getString(applicationContext.getString(R.string.location_comp_preference), applicationContext.getString(R.string.location_comp_preference_default_value)))
        } else {
            return Result.retry()
        }

        return Result.success()
    }

    private fun fetchWeatherDataForCurrentLocation( location : Location?) {
        if(location == null) {
            return
        }

        val data = weatherApi.getCurrentWeather(location.latitude, location.longitude, applicationContext.getString(R.string.flag_current))

        val db = WeatherDataDB.getDatabase(applicationContext)

        db.weatherDataDAO().addWeatherData(data)

        LocalBroadcastManager.getInstance(applicationContext).sendBroadcast(
            Intent(applicationContext.getString(R.string.weather_data_change))
        )
    }

    private fun fetchWeatherDataForComparisonLocation ( city_id : String? ) {
        if(city_id == null || city_id == applicationContext.getString(R.string.location_comp_preference_default_value)) {
            return
        }

        val data = weatherApi.getWeatherByCityId(city_id, applicationContext.getString(R.string.flag_comparison))!!

        val db = WeatherDataDB.getDatabase(applicationContext)

        db.weatherDataDAO().addWeatherData(data)

        LocalBroadcastManager.getInstance(applicationContext).sendBroadcast(
            Intent(applicationContext.getString(R.string.weather_data_change))
        )
    }
}
