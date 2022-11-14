package com.example.tele2demo.di

import com.example.tele2demo.data.*
import com.example.tele2demo.domain.LocalRepository
import com.example.tele2demo.domain.Repository
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidContext
import org.koin.core.qualifier.named
import org.koin.dsl.bind
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

private const val CONNECTION_TIMEOUT = 60000L
private const val READ_TIMEOUT = 60000L
private const val NETWORK_CLIENT = "tele2_demo_okhttp_client"
private const val NETWORK_SERVICE = "tele2_demo_retrofit"

val networkModule = module {
    single { AuthInterceptor(userData = get()) }
    single { provideGson() }
    single(named(NETWORK_CLIENT)) {
        createOkHttpClient(interceptor = get())
    }
    single(named(NETWORK_SERVICE)) {
        createApiService(
            okHttpClient = get(named(NETWORK_CLIENT)),
            gson = get()
        )
    }
    single { get<Retrofit>(named(NETWORK_SERVICE)).create(NetworkDataSource::class.java) }
    factory {
        DefaultRepository(
            dataSource = get(),
            deviceInfoMapper = DeviceInfoMapper()
        )
    }.bind(Repository::class)
    factory { DefaultLocalRepository(androidContext()) }.bind(LocalRepository::class)
}

private fun createApiService(
    okHttpClient: OkHttpClient,
    gson: Gson
): Retrofit {
    return Retrofit.Builder()
        .baseUrl("http://78.40.109.227:8080/")
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()
}

private fun createOkHttpClient(
    interceptor: AuthInterceptor
): OkHttpClient {
    val okHttpClientBuilder = OkHttpClient.Builder()
        .addInterceptor(interceptor)
        .connectTimeout(CONNECTION_TIMEOUT, TimeUnit.MILLISECONDS)
        .readTimeout(READ_TIMEOUT, TimeUnit.MILLISECONDS)

    return okHttpClientBuilder.build()
}

private fun provideGson() = GsonBuilder().setLenient().create()