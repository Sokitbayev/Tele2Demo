package com.example.tele2demo.data

import com.example.tele2demo.data.model.*
import com.example.tele2demo.domain.model.Branch
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Query

interface NetworkDataSource {

    @GET("cities")
    suspend fun getCities(): Response<List<CityResponseApi>>

    @GET("branches")
    suspend fun getBranches(@Query("cityId") cityId: String): Response<List<BranchResponseApi>>

    @GET("devices")
    suspend fun getDeviceInfo(
        @Query("barcode") barcode: String,
        @Query("branchId") branchId: String
    ): Response<DeviceInfoResponseApi>
}