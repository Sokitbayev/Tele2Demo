package com.example.tele2demo.presentaion

import android.annotation.SuppressLint
import android.app.Service
import android.content.Context
import android.content.Intent
import android.graphics.PixelFormat
import android.graphics.Point
import android.os.Binder
import android.os.IBinder
import android.view.*
import com.example.tele2demo.R
import com.example.tele2demo.databinding.WidgetBinding
import com.example.tele2demo.domain.DecimalFormatter
import com.example.tele2demo.domain.LocalRepository
import org.koin.android.ext.android.inject

class WidgetService : Service(), View.OnTouchListener, View.OnClickListener {

    private val localRepository: LocalRepository by inject()
    private val decimalFormatter = DecimalFormatter()

    private lateinit var windowManager: WindowManager

    private lateinit var contentParams: WindowManager.LayoutParams

    private val binder = LocalBinder()


    private var initialContentX = 0
    private var initialContentY = 0
    private var initialTouchY = 0.0f
    private var initialTouchX = 0.0f
    private var isMoved = false


    private var _binding: WidgetBinding? = null
    private val binding get() = _binding!!

    override fun onCreate() {
        super.onCreate()

        windowManager = getSystemService(Context.WINDOW_SERVICE) as WindowManager

        initView()
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun initView() {
        val layoutFlag = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY

        val inflater = LayoutInflater.from(ContextThemeWrapper(this, R.style.Theme_Tele2Demo))
        _binding = WidgetBinding.inflate(inflater)
        binding.priceWidget.text =
            decimalFormatter.formatText(localRepository.getDeviceInfo()!!.price.new + " ₸")

        binding.root.setOnTouchListener(this)

        contentParams = WindowManager.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT,
            layoutFlag,
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
            PixelFormat.TRANSPARENT
        )
        contentParams.gravity = Gravity.TOP or Gravity.START

        val resolution = Point()
        windowManager.defaultDisplay.getSize(resolution)

        contentParams.x = resolution.x - 300
        contentParams.y = resolution.y - 300

        windowManager.addView(binding.root, contentParams)
    }

    override fun onBind(intent: Intent): IBinder = binder

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouch(v: View?, event: MotionEvent?): Boolean {
        v ?: return false
        when (event!!.action) {
            MotionEvent.ACTION_DOWN -> {

                initialContentX = contentParams.x
                initialContentY = contentParams.y

                initialTouchX = event.rawX
                initialTouchY = event.rawY
            }
            MotionEvent.ACTION_UP -> {
                if (isMoved.not())
                    v.performClick()
                isMoved = false
            }
            MotionEvent.ACTION_MOVE -> {
                val offsetX = (event.rawX - initialTouchX).toInt()
                val offsetY = (event.rawY - initialTouchY).toInt()

                contentParams.x = initialContentX + offsetX
                contentParams.y = initialContentY + offsetY

                windowManager.updateViewLayout(binding.root, contentParams)

                if (initialContentX != contentParams.x || initialContentY != contentParams.y)
                    isMoved = true
            }
        }

        return true
    }

    override fun onClick(v: View?) {
        val intent1 = Intent()
        intent1.setClassName("kz.telecom.tele2demo", "kz.telecom.tele2demo.MainActivity")
        intent1.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        this.startActivity(intent1)
    }

    fun removeView() {
        windowManager.removeView(binding.widgetContainer)
    }

    inner class LocalBinder : Binder() {
        fun getService(): WidgetService = this@WidgetService
    }
}