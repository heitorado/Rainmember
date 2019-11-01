package br.ufpe.cin.android.rainmember.dashboard


import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.ufpe.cin.android.rainmember.AlarmsFragment
import br.ufpe.cin.android.rainmember.R

class DashboardFragment : Fragment() {

    companion object {
        const val TAG = "DashboardFragment"
        const val DASHBOARD_PREFERENCES = "DASHBOARD_PREFERENCES"
    }

    private var dataComponents = emptyList<Fragment>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d(TAG, "Created")

        val sharedPrefs = context?.getSharedPreferences(DASHBOARD_PREFERENCES, Context.MODE_PRIVATE)

        dataComponents = dataComponentFactory(sharedPrefs)

        val fragmentTransaction = fragmentManager?.beginTransaction()
        for (dataComponent in dataComponents) {
            fragmentTransaction?.add(R.id.component_container, dataComponent)
        }
        fragmentTransaction?.commit()

        return inflater.inflate(R.layout.fragment_dashboard, container, false)
    }

    override fun onDestroy() {
        val fragmentTransaction = fragmentManager?.beginTransaction()
        for (dataComponent in dataComponents) {
            fragmentTransaction?.remove(dataComponent)
        }
        fragmentTransaction?.commit()

        super.onDestroy()
    }

}
