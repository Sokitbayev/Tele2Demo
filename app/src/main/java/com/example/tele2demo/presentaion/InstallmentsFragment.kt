package com.example.tele2demo.presentaion

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import coil.decode.SvgDecoder
import coil.load
import com.example.tele2demo.R
import com.example.tele2demo.databinding.FragmentInstallmentsBinding
import com.example.tele2demo.domain.model.DeviceInfo
import org.koin.androidx.viewmodel.ext.android.viewModel

class InstallmentsFragment : Fragment() {

    private val viewModel: InstallmentViewModel by viewModel()
    private var _binding: FragmentInstallmentsBinding? = null
    private val binding get() = _binding!!
    private var link = ""
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentInstallmentsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.deviceInfo.observe(viewLifecycleOwner) {
            initView(it)
            link = it.shopUrls[0].link
        }
        binding.returnBtn.setOnClickListener {
            findNavController().navigateUp()
        }
        initClickEvent()
    }

    private fun initClickEvent() = with(binding){
        item1.setOnClickListener {
            navigate()
        }
        item2.setOnClickListener {
            navigate()
        }
        item3.setOnClickListener {
            navigate()
        }
    }

    private fun navigate() {
        val action = InstallmentsFragmentDirections.actionInstallmentsFragmentToQrBottomSheetFragment(link)
        findNavController().navigate(action)

    }

    private fun initView(deviceInfo: DeviceInfo) = with(binding) {
        item1Img.load(deviceInfo.shopUrls[0].iconUrl){
            decoderFactory { result, options, _ -> SvgDecoder(result.source, options) }
        }
        item1Text.text = deviceInfo.shopUrls[0].name
        item2Img.load(deviceInfo.shopUrls[1].iconUrl){
            decoderFactory { result, options, _ -> SvgDecoder(result.source, options) }
        }
        item2Text.text = deviceInfo.shopUrls[1].name
        item3Img.load(deviceInfo.shopUrls[2].iconUrl){
            decoderFactory { result, options, _ -> SvgDecoder(result.source, options) }
        }
        item3Text.text = deviceInfo.shopUrls[2].name
    }
}