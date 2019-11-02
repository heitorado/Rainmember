package br.ufpe.cin.android.rainmember.dashboard

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.preference.PreferenceManager
import br.ufpe.cin.android.rainmember.R
import kotlin.math.absoluteValue

fun dataComponentFactory (context: Context?): List<Fragment> {

    val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)

    var result= mutableListOf<Fragment>()

    for (key in sharedPreferences?.all!!.iterator()) {
            Log.d("dataComponentFactory", key.toString())
    }

    if (sharedPreferences != null && context != null) {

        if (isActive(sharedPreferences, context.getString(R.string.umbrella_preference))) {
            Log.d ("dataComponentFactory", "Add UmbrellaFragment")
            result.add(UmbrellaFragment())
        }
    }

    return result
}

private fun isActive (sharedPreferences: SharedPreferences, prefKey: String): Boolean {
        return sharedPreferences.getBoolean(prefKey, false)
}

