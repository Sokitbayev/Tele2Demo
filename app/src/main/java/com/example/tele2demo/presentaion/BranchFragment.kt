package com.example.tele2demo.presentaion

import android.R
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.tele2demo.databinding.FragmentSalonBinding
import com.example.tele2demo.domain.ViewState
import com.journeyapps.barcodescanner.ScanContract
import com.journeyapps.barcodescanner.ScanIntentResult
import com.journeyapps.barcodescanner.ScanOptions
import org.koin.androidx.viewmodel.ext.android.viewModel

class BranchFragment : Fragment() {

    private val viewModel: BranchViewModel by viewModel()
    private var _binding: FragmentSalonBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentSalonBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val barcodeLauncher = registerForActivityResult(
            ScanContract()
        ) { result: ScanIntentResult ->
            viewModel.onScannerResult(result.contents)
        }
        val options = ScanOptions()
        options.setDesiredBarcodeFormats(ScanOptions.EAN_13)
        options.setPrompt("POWERED BY DIGITAL")
        options.setCameraId(0)
        options.setOrientationLocked(false)
        options.setBeepEnabled(false)
        options.setBarcodeImageEnabled(true)
        binding.enterBtn.setOnClickListener {
            barcodeLauncher.launch(options)
        }
        viewModel.viewState.observe(viewLifecycleOwner) {
            when (it) {
                is ViewState.Data -> onState(it.data)
                is ViewState.Error -> TODO()
                is ViewState.Loading -> TODO()
            }
        }
        binding.chooseCity.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                viewModel.onCitySelected(p2)
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }
        binding.chooseBranch.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                viewModel.onBranchSelected(p2)
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun onState(state: BranchViewState) {
        when (state) {
            is BranchViewState.Branches -> {
                val adapter = ArrayAdapter(
                    requireContext(),
                    R.layout.simple_spinner_dropdown_item,
                    state.branches.map { it.name }
                )
                binding.chooseBranch.adapter = adapter
            }
            is BranchViewState.Cities -> {
                val adapter = ArrayAdapter(
                    requireContext(),
                    R.layout.simple_spinner_dropdown_item,
                    state.cities.map { it.name }
                )
                binding.chooseCity.adapter = adapter
            }
            is BranchViewState.OnDataReady -> {
                val action = BranchFragmentDirections.actionSecondFragmentToConfirmDeviceFragment(
                    state.deviceId,
                    state.branchId
                )
                findNavController().navigate(action)
            }
        }
    }

    private fun openScanner() {
        val barcodeLauncher = registerForActivityResult(
            ScanContract()
        ) { result: ScanIntentResult ->
            viewModel.onScannerResult(result.contents)
        }
        val options = ScanOptions()
        options.setDesiredBarcodeFormats(ScanOptions.EAN_13)
        options.setPrompt("POWERED BY DIGITAL")
        options.setCameraId(0)
        options.setOrientationLocked(false)
        options.setBeepEnabled(false)
        options.setBarcodeImageEnabled(true)
        barcodeLauncher.launch(options)
    }
}