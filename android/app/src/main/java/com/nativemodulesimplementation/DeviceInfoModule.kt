package com.nativemodulesimplementation

import android.content.Context
import android.os.Build
import android.util.DisplayMetrics
import android.view.WindowManager
import com.facebook.react.bridge.Arguments
import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.bridge.ReactContextBaseJavaModule
import com.facebook.react.bridge.ReactMethod
import com.facebook.react.bridge.Promise
import com.facebook.react.bridge.WritableMap
import com.facebook.react.uimanager.annotations.ReactProp


class DeviceInfoModule(reactcontext: ReactApplicationContext):ReactContextBaseJavaModule(reactcontext) {

    override fun getName(): String {
        return "DeviceInfoModule"
    }

    @ReactMethod
    fun getDeviceInfo(promise: Promise) {
        try {
            val metrics = getDisplayMetrics(reactApplicationContext)
            val deviceInfo: WritableMap = Arguments.createMap().apply {
                putString("model", Build.MODEL)
                putString("manufacturer", Build.MANUFACTURER)
                putString("version", Build.VERSION.RELEASE)
                putInt("apiLevel", Build.VERSION.SDK_INT)
                putString("brand", Build.BRAND)
                putString("device", Build.DEVICE)
                putString("hardware", Build.HARDWARE)
                putString("product", Build.PRODUCT)
                putString("id", Build.ID)
                putInt("width", metrics.widthPixels)
                putInt("height", metrics.heightPixels)
                putString("density", metrics.density.toString())
                putString("display", "${metrics.xdpi} x ${metrics.ydpi}")
            }
            promise.resolve(deviceInfo)  // Resolve with the WritableMap
        } catch (e: Exception) {
            promise.reject("Error", e)
        }
    }

    private fun getDisplayMetrics(context: Context): DisplayMetrics {
        val metrics = DisplayMetrics()
        val windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        windowManager.defaultDisplay.getMetrics(metrics)
        return metrics
    }
}