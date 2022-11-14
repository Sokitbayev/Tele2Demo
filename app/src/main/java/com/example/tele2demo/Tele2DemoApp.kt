package com.example.tele2demo

import android.app.Application
import com.example.tele2demo.di.mainModule
import com.example.tele2demo.di.networkModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class Tele2DemoApp : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@Tele2DemoApp)
            modules(networkModule, mainModule)
        }
    }
}