package com.she.ui.components

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.she.ui.R
import com.she.ui.databinding.FragmentButtonDemoBinding

class ButtonDemoFragment : Fragment() {
    private var _binding: FragmentButtonDemoBinding? = null
    private val binding get() = _binding!!

    private val buttonTypeList = listOf(PRIMARY, SECONDARY)
    private val buttonColorList = listOf(BLUE, WHITE, ORANGE)
    private var currentButtonColor = BLUE
    private var currentButtonType = PRIMARY

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentButtonDemoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //handle spinner for applying button type
        val spinnerTypeAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, buttonTypeList)
        spinnerTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerButtonType.adapter = spinnerTypeAdapter
        binding.spinnerButtonType.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(adapterV: AdapterView<*>?, v: View?, position: Int, id: Long) {
                binding.btnSehatq.setButtonType(buttonTypeList[position])
                currentButtonType = buttonTypeList[position]
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                //If needed
            }
        }

        //handle spinner for applying button color
        val spinnerColorAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, buttonColorList)
        spinnerColorAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerButtonColor.adapter = spinnerColorAdapter
        binding.spinnerButtonColor.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(adapterV: AdapterView<*>?, v: View?, position: Int, id: Long) {
                when(buttonColorList[position]) {
                    BLUE -> { binding.btnSehatq.setButtonColor(resources.getColor(R.color.blue)) }
                    WHITE -> { binding.btnSehatq.setButtonColor(resources.getColor(R.color.white)) }
                    ORANGE -> { binding.btnSehatq.setButtonColor(resources.getColor(R.color.orange)) }
                }
                currentButtonColor = buttonColorList[position]
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                //If needed
            }
        }

        binding.cbButtonEnable.setOnCheckedChangeListener { _, isChecked ->
            binding.btnSehatq.isEnabled = isChecked
            binding.btnSehatq.setButtonType(currentButtonType)
            when (currentButtonColor) {
                BLUE -> { binding.btnSehatq.setButtonColor(resources.getColor(R.color.blue)) }
                WHITE -> { binding.btnSehatq.setButtonColor(resources.getColor(R.color.white)) }
                ORANGE -> { binding.btnSehatq.setButtonColor(resources.getColor(R.color.orange)) }
            }
        }

        binding.cbButtonShadow.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked)
                binding.btnSehatq.elevation = resources.getDimension(R.dimen.dimen_4)
            else
                binding.btnSehatq.elevation = 0.0f
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private companion object {
        //button variant type
        const val PRIMARY = "primary"
        const val SECONDARY = "secondary"

        //button color
        const val BLUE = "blue"
        const val WHITE = "white"
        const val ORANGE = "orange"
    }
}