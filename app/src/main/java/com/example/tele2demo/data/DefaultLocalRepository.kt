package com.example.tele2demo.data

import android.content.Context
import android.content.SharedPreferences
import com.example.tele2demo.domain.LocalRepository
import com.example.tele2demo.domain.model.DeviceInfo
import com.google.gson.Gson

private const val GLOBAL_KEY_PREF = "GLOBAL_KEY_PREF"
private const val DEVICE_INFO_KEY_PREF = "DEVICE_INFO_KEY_PREF"
private const val AUTH_TOKEN_KEY_PREF = "AUTH_TOKEN_KEY_PREF"

class DefaultLocalRepository(context: Context) : LocalRepository {

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences(GLOBAL_KEY_PREF, Context.MODE_PRIVATE)
    private var editor: SharedPreferences.Editor = sharedPreferences.edit()
    private val gson = Gson()

    override fun setToken(token: String) {
        editor.putString(AUTH_TOKEN_KEY_PREF, token)
        editor.apply()
    }

    override fun getToken(): String? {
        return sharedPreferences.getString(AUTH_TOKEN_KEY_PREF, null)
    }

    override fun saveDeviceInfo(deviceInfo: DeviceInfo) {
        val json = gson.toJson(deviceInfo)
        editor.putString(DEVICE_INFO_KEY_PREF, json)
        editor.apply()
    }

    override fun getDeviceInfo(): DeviceInfo {
        val json = sharedPreferences.getString(DEVICE_INFO_KEY_PREF, "")
        val deviceInfo = gson.fromJson(json, DeviceInfo::class.java)
        return deviceInfo
    }
}