package com.example.tele2demo.domain

import com.example.tele2demo.domain.model.Branch
import com.example.tele2demo.domain.model.City
import com.example.tele2demo.domain.model.DeviceInfo

interface Repository {

    suspend fun getCities(): Response<List<City>>

    suspend fun getBranches(cityId: String): Response<List<Branch>>

    suspend fun getDeviceInfo(branchId: String, deviceId: String): Response<DeviceInfo>
}