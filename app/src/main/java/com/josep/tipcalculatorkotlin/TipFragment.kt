package com.josep.tipcalculatorkotlin

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.navigation.findNavController
import java.text.NumberFormat

class TipFragment : Fragment() {

    private lateinit var tipSeekBar: SeekBar
    private var subtotal: Int = 0
    private var numOfGuests: Int = 0
    private var tipPercent: Double = 0.0
    private var finalTotal: Double = 0.0
    private lateinit var rootView: View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        rootView = inflater.inflate(R.layout.fragment_tip, container, false)

        val args = TipFragmentArgs.fromBundle(requireArguments())

        subtotal = args.subtotal
        val subtotalTextView : TextView = rootView.findViewById(R.id.subtotal_textView)
        subtotalTextView.text = NumberFormat.getCurrencyInstance().format(subtotal)


        setUpRadioButtons()
        setUpTipSeekBar()
        setUpNumOfGuestsSpinner()

        val nextButton : Button = rootView.findViewById(R.id.next_button)
        val onClickListener = View.OnClickListener { v: View? ->

            val total = (subtotal + (subtotal * tipPercent)).toFloat()
            val action = TipFragmentDirections.actionTipFragmentToFinalTotalFragment(total, numOfGuests)
            rootView.findNavController().navigate(action)

        }
        nextButton.setOnClickListener(onClickListener)

        return rootView
    }

    private fun setUpRadioButtons(){
        val listOfButtons = listOf(rootView.findViewById<RadioButton>(R.id.ten_percent_radioButton),
            rootView.findViewById(R.id.fifteen_percent_radioButton),
            rootView.findViewById(R.id.eighteen_percent_radioButton),
            rootView.findViewById(R.id.twentyfive_percent_radioButton))
        for(item in listOfButtons){
            item.setOnClickListener { v ->
                when(v.id){
                    R.id.ten_percent_radioButton -> tipPercent = .1
                    R.id.fifteen_percent_radioButton -> tipPercent = .15
                    R.id.eighteen_percent_radioButton -> tipPercent = .18
                    R.id.twentyfive_percent_radioButton -> tipPercent = .2
                }
                tipSeekBar.progress = (tipPercent * 100).toInt()
                setTipAndTotalTextViews()
            }
        }
    }

    private fun setUpTipSeekBar(){
        tipSeekBar = rootView.findViewById(R.id.tip_seekbar)
        tipSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {}
            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                if (seekBar != null) {
                    tipPercent = (seekBar.progress / 100.0)
                }
                setTipAndTotalTextViews()
                setRadioButtonAsChecked()
            }
        })
    }

    private fun setUpNumOfGuestsSpinner(){
        val numOfGuestsSpinner: Spinner = rootView.findViewById(R.id.guests_spinner)
        val spinnerAdapter =
            context?.let { ArrayAdapter.createFromResource(it, R.array.guests_number, android.R.layout.simple_spinner_item) }
        if (spinnerAdapter != null) {
            spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        }

        val itemSelectedListener: AdapterView.OnItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(adapterView: AdapterView<*>, childView: View?, position: Int, itemId: Long) {
                numOfGuests = ( Integer.parseInt(adapterView.getItemAtPosition(position).toString()))
            }

            override fun onNothingSelected(adapterView: AdapterView<*>?) {  }
        }
        numOfGuestsSpinner.adapter = spinnerAdapter
        numOfGuestsSpinner.onItemSelectedListener = itemSelectedListener
    }

    fun setRadioButtonAsChecked(){
        val radioGroup = rootView.findViewById<RadioGroup>(R.id.radio_group)
        radioGroup.clearCheck()
        when(tipPercent){
            .10 -> rootView.findViewById<RadioButton>(R.id.ten_percent_radioButton).isChecked = true
            .15 -> rootView.findViewById<RadioButton>(R.id.fifteen_percent_radioButton).isChecked = true
            .18 -> rootView.findViewById<RadioButton>(R.id.eighteen_percent_radioButton).isChecked = true
            .25 -> rootView.findViewById<RadioButton>(R.id.twentyfive_percent_radioButton).isChecked = true
        }
    }

    fun setTipAndTotalTextViews(){
        val tipPercentTextView = rootView.findViewById<TextView>(R.id.tip_amount_textView)
        tipPercentTextView.text = "${(tipPercent * 100).toInt()}%"

        finalTotal = subtotal + (subtotal * tipPercent)

        val subtotalTextView = rootView.findViewById<TextView>(R.id.total_with_tip_textView)
        subtotalTextView.text = "$${finalTotal.toInt()}"
    }
}