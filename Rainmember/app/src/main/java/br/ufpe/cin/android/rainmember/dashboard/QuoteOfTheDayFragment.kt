package br.ufpe.cin.android.rainmember.dashboard

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import br.ufpe.cin.android.rainmember.R
import kotlinx.android.synthetic.main.fragment_quote_of_the_day.*
import kotlinx.android.synthetic.main.fragment_quote_of_the_day.view.*
import java.time.LocalDateTime
import java.util.*

class QuoteOfTheDayFragment : Fragment() {

    companion object {
        const val TAG = "QuoteOfTheDayFragment"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view =  inflater.inflate(R.layout.fragment_quote_of_the_day, container, false)
        val quoteArray = resources.getStringArray(R.array.quote_list)

        val quote = quoteArray[getIndexBasedOnCurrentDate(quoteArray.size)]

        view.quote_message.text = quote

        return view
    }

    private fun getIndexBasedOnCurrentDate (maxIndex: Int): Int {
        val now = Calendar.getInstance()

        val day = now.get(Calendar.DAY_OF_MONTH)
        val month = now.get(Calendar.MONTH)

        return (day + month) % maxIndex
    }

}
