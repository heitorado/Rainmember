package br.ufpe.cin.android.rainmember.dashboard

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.preference.PreferenceManager
import br.ufpe.cin.android.rainmember.R
import br.ufpe.cin.android.rainmember.br.ufpe.cin.android.rainmember.dashboard.SunscreenFragment
import br.ufpe.cin.android.rainmember.br.ufpe.cin.android.rainmember.dashboard.TempCompFragment

const val TAG = "dataComponentFactory"

fun dataComponentFactory (context: Context?): List<Fragment> {

    val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)

    var result= mutableListOf<Fragment>()

    for (key in sharedPreferences?.all!!.iterator()) {
            Log.d("dataComponentFactory", key.toString())
    }

    if (sharedPreferences != null && context != null) {

        if (isActive(sharedPreferences, context.getString(R.string.umbrella_preference))) {
            Log.d (TAG, "Add UmbrellaFragment")
            result.add(UmbrellaFragment())
        }

        if (isActive(sharedPreferences, context.getString(R.string.sunscreen_preference))) {
            Log.d (TAG, "Add SunscreenFragment")
            result.add(SunscreenFragment())
        }
        if (isActive(sharedPreferences, context.getString(R.string.temp_comp_preference))) {
            Log.d (TAG, "Add TempCompFragment")
            result.add(TempCompFragment())
        }



    }

    return result
}

private fun isActive (sharedPreferences: SharedPreferences, prefKey: String): Boolean {
        return sharedPreferences.getBoolean(prefKey, false)
}

