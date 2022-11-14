package com.example.tele2demo.presentaion

import android.content.Intent
import android.media.MediaPlayer
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startForegroundService
import androidx.fragment.app.Fragment
import com.example.tele2demo.databinding.FragmentMainBinding
import android.provider.Settings;
import androidx.navigation.fragment.findNavController
import coil.ImageLoader
import coil.decode.SvgDecoder
import coil.load
import coil.size.Precision
import coil.size.Scale
import com.example.tele2demo.R
import com.example.tele2demo.domain.ViewState
import com.example.tele2demo.domain.model.DeviceInfo
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel


class MainFragment : Fragment() {

    private val widgetServiceMonitor: WidgetServiceMonitor by inject()
    private var mediaPlayer: MediaPlayer? = null
    private val viewModel: MainViewModel by viewModel()
    private var _binding: FragmentMainBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        checkOverlayPermission()
        startService()
        viewModel.viewState.observe(viewLifecycleOwner) {
            when (it) {
                is ViewState.Data -> onData(it.data)
                is ViewState.Error -> {}
                is ViewState.Loading -> {}
            }
        }
        binding.buyBtn.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_installmentsFragment)
        }
        binding.secretBtn.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_FirstFragment)
        }
        binding.closeDisplay.setOnClickListener {
            requireActivity().onBackPressed()
        }
        mediaPlayer = MediaPlayer.create(requireContext(), R.raw.sound)
        binding.needHelp.setOnClickListener {
            mediaPlayer?.start()
        }
    }

    private fun onData(state: MainViewState) {
        when (state) {
            is MainViewState.DataReady -> initView(state.deviceInfo)
        }
    }

    private fun initView(deviceInfo: DeviceInfo) = with(binding) {
        /*bannerImg.load(deviceInfo.banner.imageUrl) {
            scale(Scale.FIT)
        }*/
        deviceTitle.text = deviceInfo.title
        deviceSubTitle.text = deviceInfo.subTitle
        oldPrice.text = deviceInfo.price.old
        newPrice.text = deviceInfo.price.new
        devicePricePerMonth.text = deviceInfo.installmentPlan.price
        months.text = deviceInfo.installmentPlan.months
        tariffItemImage1.load(deviceInfo.tariff[0].iconUrl) {
            decoderFactory { result, options, _ -> SvgDecoder(result.source, options) }
        }
        tariffItemText1.text = deviceInfo.tariff[0].name.ru
        tariffItemImage2.load(deviceInfo.tariff[1].iconUrl)
        {
            decoderFactory { result, options, _ -> SvgDecoder(result.source, options) }
        }
        tariffItemText2.text = deviceInfo.tariff[1].name.ru
        tariffItemImage3.load(deviceInfo.tariff[2].iconUrl) {
            decoderFactory { result, options, _ -> SvgDecoder(result.source, options) }
        }
        tariffItemText3.text = deviceInfo.tariff[2].name.ru
    }

    private fun startService() {
        if (Settings.canDrawOverlays(requireContext())) {
            //widgetServiceMonitor.init(requireActivity(), requireActivity().lifecycle)
        }
    }

    private fun checkOverlayPermission() {
        if (!Settings.canDrawOverlays(requireContext())) {
            val myIntent = Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION)
            startActivity(myIntent)
        }
    }

    override fun onResume() {
        super.onResume()
        //startService()
    }
}