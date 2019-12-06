package br.ufpe.cin.android.rainmember.dashboard

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.ufpe.cin.android.rainmember.br.ufpe.cin.android.rainmember.PreferencesActivity
import br.ufpe.cin.android.rainmember.R
import kotlinx.android.synthetic.main.fragment_dashboard.view.*

class DashboardFragment : Fragment() {

    companion object {
        const val TAG = "DashboardFragment"
    }

    private var dataComponents = emptyList<Fragment>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d(TAG, "Created")

        dataComponents = dataComponentFactory(context)

        addComponents()

        val view = inflater.inflate(R.layout.fragment_dashboard, container, false)

        view.action_settings.setOnClickListener {
            val intent = Intent(context, PreferencesActivity::class.java)

            startActivity(intent)
        }

        return view
    }

    private fun addComponents () {
        val fragmentTransaction = fragmentManager?.beginTransaction()
        var index = 1
        for (dataComponent in dataComponents) {
            fragmentTransaction?.add(R.id.component_container, dataComponent, "component_$index")
            index += 1
        }
        fragmentTransaction?.commitAllowingStateLoss()
    }

    private fun destroyComponents () {
        val fragmentTransaction = fragmentManager?.beginTransaction()
        for (dataComponent in dataComponents) {
            fragmentTransaction?.remove(dataComponent)
        }
        fragmentTransaction?.commitAllowingStateLoss()
    }

    override fun onDestroy() {
        destroyComponents()

        super.onDestroy()
    }

}
