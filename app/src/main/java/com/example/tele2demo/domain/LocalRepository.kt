package com.example.tele2demo.domain

import com.example.tele2demo.domain.model.DeviceInfo

interface LocalRepository {

    fun setToken(token: String)

    fun getToken(): String?

    fun saveDeviceInfo(deviceInfo: DeviceInfo)

    fun getDeviceInfo(): DeviceInfo?

    fun setBranchId(id: String)

    fun setDeviceId(id: String)

    fun getBranchId(): String?

    fun getDeviceId(): String?
}