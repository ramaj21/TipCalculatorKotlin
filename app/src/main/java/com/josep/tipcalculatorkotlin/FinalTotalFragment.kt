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
import com.josep.tipcalculatorkotlin.databinding.FragmentFinalTotalBinding
import java.text.NumberFormat
import kotlin.math.ceil
import kotlin.math.floor

class FinalTotalFragment : Fragment() {

    private var _binding : FragmentFinalTotalBinding? = null
    private val binding get() = _binding!!

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFinalTotalBinding.inflate(inflater, container, false)
        val rootView = binding.root

        val args = FinalTotalFragmentArgs.fromBundle(requireArguments())

        val totalFinal = args.total
        val numOfGuests = args.numOfGuests

        binding.totalTextView.text ="Total With Tip: ${NumberFormat.getCurrencyInstance().format(totalFinal)}"
        val costPerPerson : Double = (totalFinal / numOfGuests).toDouble()
        binding.dividedCostTextView.text = "Cost Per Person: ${NumberFormat.getCurrencyInstance().format(costPerPerson)}"

        val onClickListener = View.OnClickListener { v: View? ->
            when(v?.id){
                binding.roundUpButton.id -> binding.dividedCostTextView.text = "Cost Per Person: ${NumberFormat.getCurrencyInstance().format(ceil(costPerPerson))}"
                binding.roundDownButton.id -> binding.dividedCostTextView.text = "Cost Per Person: ${NumberFormat.getCurrencyInstance().format(floor(costPerPerson))}"
            }
        }
        binding.roundUpButton.setOnClickListener(onClickListener)
        binding.roundDownButton.setOnClickListener(onClickListener)

        return rootView
    }
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}