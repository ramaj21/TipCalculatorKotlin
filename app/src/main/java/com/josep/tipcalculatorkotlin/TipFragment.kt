package com.josep.tipcalculatorkotlin

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.navigation.findNavController
import com.josep.tipcalculatorkotlin.databinding.FragmentTipBinding
import java.text.NumberFormat

class TipFragment : Fragment() {

    private lateinit var tipSeekBar: SeekBar
    private var subtotal: Int = 0
    private var numOfGuests: Int = 0
    private var tipPercent: Double = 0.0
    private var finalTotal: Double = 0.0

    private var _binding : FragmentTipBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTipBinding.inflate(inflater, container, false)
        val rootView = binding.root

        val args = TipFragmentArgs.fromBundle(requireArguments())

        subtotal = args.subtotal
        binding.subtotalTextView.text = NumberFormat.getCurrencyInstance().format(subtotal)


        setUpRadioButtons()
        setUpTipSeekBar()
        setUpNumOfGuestsSpinner()

        val onClickListener = View.OnClickListener { v: View? ->

            val total = (subtotal + (subtotal * tipPercent)).toFloat()
            val action = TipFragmentDirections.actionTipFragmentToFinalTotalFragment(total, numOfGuests)
            binding.root.findNavController().navigate(action)

        }
        binding.nextButton.setOnClickListener(onClickListener)

        return rootView
    }

    private fun setUpRadioButtons(){
        val listOfButtons = listOf(binding.tenPercentRadioButton,
            binding.fifteenPercentRadioButton,
            binding.eighteenPercentRadioButton,
            binding.twentyfivePercentRadioButton)
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
        binding.tipSeekbar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
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
        val spinnerAdapter =
            context?.let { ArrayAdapter.createFromResource(it, R.array.guests_number, android.R.layout.simple_spinner_item) }
        spinnerAdapter?.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        val itemSelectedListener: AdapterView.OnItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(adapterView: AdapterView<*>, childView: View?, position: Int, itemId: Long) {
                numOfGuests = ( Integer.parseInt(adapterView.getItemAtPosition(position).toString()))
            }

            override fun onNothingSelected(adapterView: AdapterView<*>?) {  }
        }
        binding.guestsSpinner.adapter = spinnerAdapter
        binding.guestsSpinner.onItemSelectedListener = itemSelectedListener
    }

    fun setRadioButtonAsChecked(){
        binding.radioGroup.clearCheck()
        when(tipPercent){
            .10 -> binding.tenPercentRadioButton.isChecked = true
            .15 -> binding.fifteenPercentRadioButton.isChecked = true
            .18 -> binding.eighteenPercentRadioButton.isChecked = true
            .25 -> binding.twentyfivePercentRadioButton.isChecked = true
        }
    }

    fun setTipAndTotalTextViews(){
        binding.tipAmountTextView.text = "${(tipPercent * 100).toInt()}%"

        finalTotal = subtotal + (subtotal * tipPercent)

        binding.totalWithTipTextView.text = "$${finalTotal.toInt()}"
    }
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}