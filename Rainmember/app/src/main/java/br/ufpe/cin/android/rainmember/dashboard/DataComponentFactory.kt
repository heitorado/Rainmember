package br.ufpe.cin.android.rainmember.dashboard

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.preference.PreferenceManager
import br.ufpe.cin.android.rainmember.R
import br.ufpe.cin.android.rainmember.br.ufpe.cin.android.rainmember.dashboard.LifeSuggestionFragment
import br.ufpe.cin.android.rainmember.br.ufpe.cin.android.rainmember.dashboard.SunscreenFragment

const val TAG = "dataComponentFactory"

fun dataComponentFactory (context: Context?): List<Fragment> {

    val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)

    var result= mutableListOf<Fragment>()

    if (sharedPreferences != null && context != null) {

        if (isActive(sharedPreferences, context.getString(R.string.umbrella_preference))) {
            result.add(UmbrellaFragment())
        }

        if (isActive(sharedPreferences, context.getString(R.string.sunscreen_preference))) {
            result.add(SunscreenFragment())
        }

        if (isActive(sharedPreferences, context.getString(R.string.temp_comp_preference))) {
            result.add(TempCompFragment())
        }

        if (isActive(sharedPreferences, context.getString(R.string.life_suggestion_preference))) {
            result.add(LifeSuggestionFragment())
        }
        
        if (isActive(sharedPreferences, context.getString(R.string.clothing_suggestion_preference))) {
            result.add(ClothingSuggestionFragment())
        }

        if (isActive(sharedPreferences, context.getString(R.string.quote_of_the_day_preference))) {
            result.add(QuoteOfTheDayFragment())
        }
    }

    return result
}

private fun isActive (sharedPreferences: SharedPreferences, prefKey: String): Boolean {
        return sharedPreferences.getBoolean(prefKey, false)
}

