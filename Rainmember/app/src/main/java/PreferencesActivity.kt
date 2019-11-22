package br.ufpe.cin.android.rainmember

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.PreferenceFragmentCompat
import android.content.SharedPreferences
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.preference.PreferenceManager


class PreferencesActivity : AppCompatActivity(), SharedPreferences.OnSharedPreferenceChangeListener {
    private var sharedPref: SharedPreferences? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_preferences)
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.preferences_fragment, PreferencesFragment())
            .commit()

        sharedPref = PreferenceManager.getDefaultSharedPreferences(applicationContext)
    }


    override fun onResume() {
        super.onResume()
        sharedPref?.registerOnSharedPreferenceChangeListener(this)

    }

    override fun onStop() {
        super.onStop()
        sharedPref?.unregisterOnSharedPreferenceChangeListener(this)
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, prefKey: String?) {
        val intent = Intent(applicationContext.getString(R.string.dashboard_change))

        LocalBroadcastManager.getInstance(applicationContext).sendBroadcast(intent)
    }

    class PreferencesFragment : PreferenceFragmentCompat() {
        override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
            setPreferencesFromResource(R.xml.activity_preferences, rootKey)
        }
    }

}