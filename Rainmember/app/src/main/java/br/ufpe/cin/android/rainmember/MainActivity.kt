package br.ufpe.cin.android.rainmember

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import br.ufpe.cin.android.rainmember.br.ufpe.cin.android.rainmember.alarm.AlarmsFragment
import br.ufpe.cin.android.rainmember.br.ufpe.cin.android.rainmember.data.WeatherDataWorker
import br.ufpe.cin.android.rainmember.br.ufpe.cin.android.rainmember.data.room.WeatherDataDB
import br.ufpe.cin.android.rainmember.dashboard.DashboardFragment
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.contentView
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import java.util.*
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    companion object {
        const val CURRENT_SCREEN = "current_screen"
        const val FETCH_DATA_JOB = "fetch_data_job"
        const val FETCH_DATA_JOB_INTERVAL: Long = 35
        const val ACCESS_FINE_LOCATION_REQUEST_ID = 1
    }

    private val climateInfoChangeReceiver = object: BroadcastReceiver() {
        val intentFilter : IntentFilter
        get() {
            val iFilter = IntentFilter()
            iFilter.addAction(applicationContext.getString(R.string.weather_data_change))

            return iFilter
        }

        override fun onReceive(ctx: Context?, intent: Intent?) {
            updateClimateInfoSection( applicationContext )
        }
    }

    private val dashboardChangeReceiver = object: BroadcastReceiver() {
        val intentFilter: IntentFilter
            get() {
                val iFilter = IntentFilter()

                iFilter.addAction(applicationContext.getString(R.string.dashboard_change))
                iFilter.addAction(applicationContext.getString(R.string.weather_data_change))

                return iFilter
            }

        override fun onReceive(context: Context?, intent: Intent?) {
            setNavigationScreen(currentScreen)
        }
    }

    private var currentScreen: Int = R.id.navigation_dashboard

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (savedInstanceState != null) {
            currentScreen = savedInstanceState.getInt(CURRENT_SCREEN)
        }
        setContentView(R.layout.activity_main)

        updateClimateInfoSection(applicationContext)

        setNavigationScreen(currentScreen)

        setUpPermissions()

        navigation_view.setOnNavigationItemSelectedListener {
            if (currentScreen != it.itemId) {
                setNavigationScreen(it.itemId)
            }

            true
        }

        createNotificationChannel()

        setUpDataWorker()

        LocalBroadcastManager.getInstance(applicationContext)
            .registerReceiver(dashboardChangeReceiver, dashboardChangeReceiver.intentFilter)

        LocalBroadcastManager.getInstance(applicationContext)
            .registerReceiver(climateInfoChangeReceiver, climateInfoChangeReceiver.intentFilter)
    }

    override fun onDestroy() {
        LocalBroadcastManager.getInstance(applicationContext).unregisterReceiver(dashboardChangeReceiver)
        LocalBroadcastManager.getInstance(applicationContext).unregisterReceiver(climateInfoChangeReceiver)


        super.onDestroy()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putInt(CURRENT_SCREEN, currentScreen)

        super.onSaveInstanceState(outState)
    }

    private fun createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = getString(R.string.channel_name)
            val descriptionText = getString(R.string.channel_description)
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(getString(R.string.channel_id), name, importance).apply {
                description = descriptionText
            }
            // Register the channel with the system
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun setUpDataWorker () {
        val workManager = WorkManager.getInstance(applicationContext)

        val workRequests = workManager.getWorkInfosByTag(FETCH_DATA_JOB).get()

        if (workRequests == null || workRequests.size == 0) {

            val fetchDataRequest = PeriodicWorkRequest.Builder(
                WeatherDataWorker::class.java,
                FETCH_DATA_JOB_INTERVAL,
                TimeUnit.MINUTES
            )
                .addTag(FETCH_DATA_JOB)
                .build()

            workManager.enqueue(fetchDataRequest)
        }
    }

    private fun setUpPermissions () {
        if (ContextCompat.checkSelfPermission(applicationContext, Manifest.permission.ACCESS_FINE_LOCATION) !== PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this@MainActivity,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                ACCESS_FINE_LOCATION_REQUEST_ID
            )
        }

    }

    private fun setNavigationScreen (itemId: Int) {
        val fragmentToBeOpened = when (itemId) {
            R.id.navigation_dashboard -> DashboardFragment()
            R.id.navigation_alarms -> AlarmsFragment()
            else -> DashboardFragment() // Default screen
        }

        currentScreen = itemId
        setFragment(fragmentToBeOpened)
    }

    private fun setFragment (fragment: Fragment) {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.app_container, fragment)
        fragmentTransaction.commitAllowingStateLoss()
    }

    private fun updateClimateInfoSection( ctx : Context ){
        doAsync {
            val db = WeatherDataDB.getDatabase(ctx)
            val weatherData = db.weatherDataDAO().getLatest()

            if(weatherData != null){
                uiThread {
                    currentLocation.text = weatherData.cityName.capitalize()
                    currentTemperature.text = "${"%.2f".format(Locale.ENGLISH, weatherData.temperature)}°C"
                    currentUv.text = "%.2f".format(Locale.ENGLISH, weatherData.currentUv)
                    currentWeather.text = weatherData.condition.capitalize()
                }
            }
        }
    }
}
