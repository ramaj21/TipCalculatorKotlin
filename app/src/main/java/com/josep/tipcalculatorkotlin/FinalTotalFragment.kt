package com.josep.tipcalculatorkotlin

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import java.text.NumberFormat
import kotlin.math.ceil
import kotlin.math.floor

class FinalTotalFragment : Fragment() {

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_final_total, container, false)

        val args = FinalTotalFragmentArgs.fromBundle(requireArguments())

        val totalFinal = args.total
        val numOfGuests = args.numOfGuests

        val totalTextView : TextView = rootView.findViewById(R.id.total_text_view)
        totalTextView.text ="Total With Tip: ${NumberFormat.getCurrencyInstance().format(totalFinal)}"
        val dividedCost : TextView = rootView.findViewById(R.id.divided_cost_text_view)
        val costPerPerson : Double = (totalFinal / numOfGuests).toDouble()
        dividedCost.text = "Cost Per Person: ${NumberFormat.getCurrencyInstance().format(costPerPerson)}"

        val roundUp : Button = rootView.findViewById(R.id.round_up_button)
        val roundDown : Button = rootView.findViewById(R.id.round_down_button)
        val onClickListener = View.OnClickListener { v: View? ->
            when(v?.id){
                roundUp.id -> dividedCost.text = "Cost Per Person: ${NumberFormat.getCurrencyInstance().format(ceil(costPerPerson))}"
                roundDown.id -> dividedCost.text = "Cost Per Person: ${NumberFormat.getCurrencyInstance().format(floor(costPerPerson))}"
            }
        }
        roundUp.setOnClickListener(onClickListener)
        roundDown.setOnClickListener(onClickListener)

        return rootView
    }
}