package com.example.tele2demo.presentaion

import com.example.tele2demo.domain.model.DeviceInfo

sealed class MainViewState {
    data class DataReady(val deviceInfo: DeviceInfo): MainViewState()
}