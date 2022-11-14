package com.example.tele2demo.data

import com.example.tele2demo.domain.model.UserData
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor(
    private val userData: UserData
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val request = originalRequest.newBuilder()
            .header("Authorization", "Basic ${userData.token}")
            .build()

        return chain.proceed(request)
    }
}