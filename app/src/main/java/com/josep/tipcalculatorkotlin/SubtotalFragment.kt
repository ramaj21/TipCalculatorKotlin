package com.josep.tipcalculatorkotlin

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.navigation.findNavController
import java.text.NumberFormat

class SubtotalFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_subtotal, container, false)

        var subtotal = 0

        val subtotalSumTextView : TextView = rootView.findViewById(R.id.subtotal_number)


        val numberButtons: List<Button> = listOf(rootView.findViewById(R.id.one_button),
            rootView.findViewById(R.id.two_button),
            rootView.findViewById(R.id.three_button),
            rootView.findViewById(R.id.four_button),
            rootView.findViewById(R.id.five_button),
            rootView.findViewById(R.id.six_button),
            rootView.findViewById(R.id.seven_button),
            rootView.findViewById(R.id.eight_button),
            rootView.findViewById(R.id.nine_button),
            rootView.findViewById(R.id.zero_button))
        val nextButton : ImageButton = rootView.findViewById(R.id.check_button)
        val backButton : ImageButton = rootView.findViewById(R.id.back_button)

        val onClickListener = View.OnClickListener { v: View? ->
            if(v?.id == R.id.check_button){
                if(subtotal > 0) {
                    val action =
                        SubtotalFragmentDirections.actionSubtotalFragmentToTipFragment(subtotal)
                    rootView.findNavController().navigate(action)
                }
            }
            else if(v?.id == R.id.back_button){
                subtotal /= 10
            }
            else{
                subtotal*=10
                subtotal += Integer.parseInt((v as Button).text.toString())

            }
            val newSubtotal = NumberFormat.getCurrencyInstance().format(subtotal)
            subtotalSumTextView.text = newSubtotal
        }
        for(i in numberButtons)
            i.setOnClickListener(onClickListener)
        nextButton.setOnClickListener(onClickListener)
        backButton.setOnClickListener(onClickListener)



        return rootView
    }
}