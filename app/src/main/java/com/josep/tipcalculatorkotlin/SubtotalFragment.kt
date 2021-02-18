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
import com.josep.tipcalculatorkotlin.databinding.FragmentSubtotalBinding
import java.text.NumberFormat

class SubtotalFragment : Fragment() {

    private var _binding : FragmentSubtotalBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSubtotalBinding.inflate(inflater, container, false)
        val rootView = binding.root

        var subtotal = 0



        val numberButtons: List<Button> = listOf(binding.oneButton,
            binding.twoButton, binding.threeButton, binding.fourButton, binding.fiveButton,
            binding.sixButton, binding.sevenButton, binding.eightButton, binding.nineButton,
            binding.zeroButton)

        val onClickListener = View.OnClickListener { v: View? ->
            if(v?.id == R.id.check_button){
                if(subtotal > 0) {
                    val action =
                        SubtotalFragmentDirections.actionSubtotalFragmentToTipFragment(subtotal)
                    binding.root.findNavController().navigate(action)
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
            binding.subtotalNumber.text = newSubtotal
        }
        for(i in numberButtons)
            i.setOnClickListener(onClickListener)
        binding.checkButton.setOnClickListener(onClickListener)
        binding.backButton.setOnClickListener(onClickListener)

        return rootView
    }
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}