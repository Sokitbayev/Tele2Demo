package com.example.tele2demo.presentaion

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.tele2demo.domain.LocalRepository
import com.example.tele2demo.domain.model.DeviceInfo

class InstallmentViewModel(localRepository: LocalRepository) : ViewModel() {

    private val _deviceInfo = MutableLiveData<DeviceInfo>()
    val deviceInfo: LiveData<DeviceInfo> = _deviceInfo

    init {
        _deviceInfo.value = localRepository.getDeviceInfo()
    }
}