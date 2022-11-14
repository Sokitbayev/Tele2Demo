package com.example.tele2demo.presentaion

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import android.provider.Settings
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent

class WidgetServiceMonitor : LifecycleObserver {

    private lateinit var context: Context
    private lateinit var lifecycle: Lifecycle

    private lateinit var overlayServiceIntent: Intent

    private var overlayService: WidgetService? = null

    private var overlayBound = false

    private val connection = object : ServiceConnection {

        override fun onServiceConnected(className: ComponentName, service: IBinder) {
            val binder = service as WidgetService.LocalBinder
            overlayService = binder.getService()
            overlayBound = true
        }

        override fun onServiceDisconnected(arg0: ComponentName) {
            overlayBound = false
        }
    }

    fun init(context: Context, lifecycle: Lifecycle) {
        this.context = context
        this.lifecycle = lifecycle
        lifecycle.addObserver(this)
    }

    @Suppress("unused")
    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun onResume() {
        if (overlayBound) {
            context.unbindService(connection)
            overlayBound = false
            context.stopService(overlayServiceIntent)
        }
        overlayService?.removeView()
        overlayService = null
    }

    @Suppress("unused")
    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun onStop() {
        if (getOverlayPermission()) {
            overlayServiceIntent = Intent(context, WidgetService::class.java).also { intent ->
                context.bindService(intent, connection, Context.BIND_AUTO_CREATE)
            }
            context.startService(overlayServiceIntent)
        }
    }

    @Suppress("unused")
    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy() {
        lifecycle.removeObserver(this)
    }

    private fun getOverlayPermission(): Boolean {
        return Settings.canDrawOverlays(context)
    }
}