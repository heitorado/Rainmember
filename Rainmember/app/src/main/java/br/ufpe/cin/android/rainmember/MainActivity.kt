package br.ufpe.cin.android.rainmember

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import br.ufpe.cin.android.rainmember.dashboard.DashboardFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    companion object {
        const val CURRENT_SCREEN = "current_screen"
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

        navigation_view.setOnNavigationItemSelectedListener {
            if (currentScreen != it.itemId) {
                setNavigationScreen(it.itemId)
            }

            true
        }
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
