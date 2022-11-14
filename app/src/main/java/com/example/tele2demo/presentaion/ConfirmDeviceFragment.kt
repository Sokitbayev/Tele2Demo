package com.example.tele2demo.presentaion

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.tele2demo.R
import com.example.tele2demo.databinding.FragmentConfirmDeviceBinding
import com.example.tele2demo.domain.ViewState
import com.example.tele2demo.domain.model.DeviceInfo
import org.koin.androidx.viewmodel.ext.android.viewModel


class ConfirmDeviceFragment : Fragment() {

    private val viewModel: ConfirmDeviceViewModel by viewModel()
    private val args: ConfirmDeviceFragmentArgs by navArgs()
    private var _binding: FragmentConfirmDeviceBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentConfirmDeviceBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.onData(args.deviceId, args.branchId)
        binding.barcodeEditText.setText(args.deviceId)
        viewModel.viewState.observe(viewLifecycleOwner) {
            when (it) {
                is ViewState.Data -> onData(it.data)
                is ViewState.Error -> {}
                is ViewState.Loading -> {}
            }
        }
        binding.confirmButton.setOnClickListener {
            findNavController().navigate(R.id.action_confirmDeviceFragment_to_mainFragment)
        }
    }

    private fun onData(deviceInfo: DeviceInfo) = with(binding) {
        modelEditText.setText(deviceInfo.title)
        brandEditText.setText(deviceInfo.subTitle)
        priceEditText.setText(deviceInfo.price.new)
    }

}