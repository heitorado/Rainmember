package br.ufpe.cin.android.rainmember.dashboard

import android.content.SharedPreferences
import androidx.fragment.app.Fragment
import br.ufpe.cin.android.rainmember.AlarmsFragment

fun dataComponentFactory (sharedPreferences: SharedPreferences?): List<Fragment> {
    var result= mutableListOf<Fragment>()

    if (sharedPreferences != null) {
        if (isActive(sharedPreferences, UmbrellaComponent.PREFERENCE_KEY)) {
            result.add(UmbrellaComponent())
        }
    }

    result.add(AlarmsFragment())

    return result
}

private fun isActive (sharedPreferences: SharedPreferences, prefKey: String): Boolean {
        return sharedPreferences.getBoolean(UmbrellaComponent.PREFERENCE_KEY, true)
}

