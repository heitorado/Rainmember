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
        val countryCodesFile = File(this.ctx.filesDir.absolutePath, "countryCodes.json")

        if (ContextCompat.checkSelfPermission(applicationContext, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            val location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)

            Log.d(TAG, "Fetching data for:")
            Log.d (TAG, "Lat: ${location.latitude}")
            Log.d (TAG, "Lon: ${location.longitude}")

            val data = weatherApi.getCurrentWeather(location.latitude, location.longitude)

            val db = WeatherDataDB.getDatabase(applicationContext)

            db.weatherDataDAO().addWeatherData(data)

            LocalBroadcastManager.getInstance(applicationContext).sendBroadcast(
                Intent(applicationContext.getString(R.string.weather_data_change))
            )
        }

        if(!countryCodesFile.exists()) {
            val request = Request.Builder()
                .url("https://raw.githubusercontent.com/heitorado/Rainmember/master/city.list.min.json")
                .build()

            client.newCall(request).execute().use { res ->
                if(res.body != null) {
                    countryCodesFile.appendText(res.body!!.string())
                    Log.d(TAG, res.body!!.string())
                }
            }

            Log.d(TAG, "Downloaded Country Code Data from Github!")
        }

        return Result.success()
    }
}
