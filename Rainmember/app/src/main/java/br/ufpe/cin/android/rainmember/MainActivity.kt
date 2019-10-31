package br.ufpe.cin.android.rainmember

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import br.ufpe.cin.android.rainmember.dashboard.DashboardFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    companion object {
        const val CURRENT_SCREEN = "current_screen"
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
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }

}
