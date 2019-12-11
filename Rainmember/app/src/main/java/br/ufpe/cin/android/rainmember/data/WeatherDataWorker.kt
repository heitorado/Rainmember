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

        Log.d(TAG, "Worker BOOTING")

        val locationManager = applicationContext.getSystemService(Context.LOCATION_SERVICE) as LocationManager

        if (ContextCompat.checkSelfPermission(applicationContext, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            val location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
            val sharedPref = PreferenceManager.getDefaultSharedPreferences(applicationContext)

            fetchWeatherDataForCurrentLocation(location)

            fetchWeatherDataForComparisonLocation(sharedPref.getString(applicationContext.getString(R.string.location_comp_preference), applicationContext.getString(R.string.location_comp_preference_default_value)))
        } else {
            return Result.retry()
        }

        downloadCountryCodesFile()

        return Result.success()
    }

    private fun fetchWeatherDataForCurrentLocation( location : Location?) {
        if(location == null) {
            Log.d(TAG, "Location UNAVAILABLE")
            return
        }

        Log.d(TAG, "Fetching CURRENT data for:")
        Log.d (TAG, "Lat: ${location.latitude}")
        Log.d (TAG, "Lon: ${location.longitude}")

        val data = weatherApi.getCurrentWeather(location.latitude, location.longitude, applicationContext.getString(R.string.flag_current))

        val db = WeatherDataDB.getDatabase(applicationContext)

        db.weatherDataDAO().addWeatherData(data)

        LocalBroadcastManager.getInstance(applicationContext).sendBroadcast(
            Intent(applicationContext.getString(R.string.weather_data_change))
        )
    }

    private fun fetchWeatherDataForComparisonLocation ( city_id : String? ) {
        if(city_id == null || city_id == applicationContext.getString(R.string.location_comp_preference_default_value)) {
            Log.d(TAG, "City UNAVAILABLE")
            return
        }

        Log.d(TAG, "Fetching COMPARISON data for:")
        Log.d (TAG, "CITY_ID: $city_id")

        val data = weatherApi.getWeatherByCityId(city_id, applicationContext.getString(R.string.flag_comparison))!!

        val db = WeatherDataDB.getDatabase(applicationContext)

        db.weatherDataDAO().addWeatherData(data)

        LocalBroadcastManager.getInstance(applicationContext).sendBroadcast(
            Intent(applicationContext.getString(R.string.weather_data_change))
        )
    }

    private fun downloadCountryCodesFile () {
        val countryCodesFile = File(this.ctx.filesDir.absolutePath, "countryCodes.json")

        if(!countryCodesFile.exists()) {
            val request = Request.Builder()
                .url("https://raw.githubusercontent.com/heitorado/Rainmember/master/city.list.min.json")
                .build()

            client.newCall(request).execute().use { res ->
                if(res.body != null) {
                    countryCodesFile.appendText(res.body!!.string())
                    //Log.d(TAG, res.body!!.string())
                }
            }

            Log.d(TAG, "Downloaded Country Code Data from Github!")
        }
    }
}
