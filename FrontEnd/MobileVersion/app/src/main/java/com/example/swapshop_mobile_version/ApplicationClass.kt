package com.example.swapshop_mobile_version

import android.app.Application
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

import com.onesignal.OneSignal
import com.onesignal.debug.LogLevel

const val ONESIGNAL_APP_ID = "6274096b-ffbf-40a5-8f8b-c82352e62563"

class ApplicationClass : Application() {
    override fun onCreate() {
        super.onCreate()

        OneSignal.Debug.logLevel = LogLevel.VERBOSE

        OneSignal.initWithContext(this, ONESIGNAL_APP_ID)

        CoroutineScope(Dispatchers.IO).launch {
            OneSignal.Notifications.requestPermission(true)
        }
    }
}