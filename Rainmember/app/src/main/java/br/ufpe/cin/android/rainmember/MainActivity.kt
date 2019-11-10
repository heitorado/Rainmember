package br.ufpe.cin.android.rainmember

import android.Manifest
import android.app.Activity
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import br.ufpe.cin.android.rainmember.br.ufpe.cin.android.rainmember.data.WeatherDataWorker
import br.ufpe.cin.android.rainmember.dashboard.DashboardFragment
import kotlinx.android.synthetic.main.activity_main.*
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    companion object {
        const val CURRENT_SCREEN = "current_screen"
        const val FETCH_DATA_JOB = "fetch_data_job"
        const val FETCH_DATA_JOB_INTERVAL: Long = 15
        const val ACCESS_FINE_LOCATION_REQUEST_ID = 1
    }

    private val dashboardChangeReceiver = object: BroadcastReceiver() {
        val intentFilter: IntentFilter
            get() {
                return IntentFilter(applicationContext.getString(R.string.dashboard_change))
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

        setNavigationScreen(currentScreen)

        setUpPermissions()

        navigation_view.setOnNavigationItemSelectedListener {
            if (currentScreen != it.itemId) {
                setNavigationScreen(it.itemId)
            }

            true
        }

        setUpDataWorker()

        LocalBroadcastManager.getInstance(applicationContext)
            .registerReceiver(dashboardChangeReceiver, dashboardChangeReceiver.intentFilter)
    }

    override fun onDestroy() {
        LocalBroadcastManager.getInstance(applicationContext).unregisterReceiver(dashboardChangeReceiver)

        super.onDestroy()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putInt(CURRENT_SCREEN, currentScreen)

        super.onSaveInstanceState(outState)
    }

    private fun setUpDataWorker () {
        val workManager = WorkManager.getInstance(applicationContext)


        val workRequests = workManager.getWorkInfosByTag(FETCH_DATA_JOB).get()

        //if (workRequests == null || workRequests.size == 0) {

            val fetchDataRequest = PeriodicWorkRequest.Builder(
                WeatherDataWorker::class.java,
                FETCH_DATA_JOB_INTERVAL,
                TimeUnit.MINUTES
            )
                .addTag(FETCH_DATA_JOB)
                .build()

            workManager.enqueue(fetchDataRequest)
        //}
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

}
