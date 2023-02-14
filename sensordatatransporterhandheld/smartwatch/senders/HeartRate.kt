package com.damnluck.sensordatatransporterhandheld.smartwatch.senders

import android.app.Activity
import com.google.android.gms.wearable.WearableListenerService

class HeartRate(private val activity: Activity) : WearableListenerService() {
    fun startHeartRate() {
        SendData(activity).sendToWatch("/startHeartRate", "")
    }

    fun stopHeartRate() {
        SendData(activity).sendToWatch("/stopHeartRate", "")
    }
}