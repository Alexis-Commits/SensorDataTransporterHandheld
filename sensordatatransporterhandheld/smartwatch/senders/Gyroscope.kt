package com.damnluck.sensordatatransporterhandheld.smartwatch.senders

import android.app.Activity
import com.google.android.gms.wearable.WearableListenerService

class Gyroscope(private val activity: Activity) : WearableListenerService() {
    fun startGyroscope() {
        SendData(activity).sendToWatch("/startGyroscope", "")
    }

    fun stopGyroscope() {
        SendData(activity).sendToWatch("/stopGyroscope", "")
    }
}