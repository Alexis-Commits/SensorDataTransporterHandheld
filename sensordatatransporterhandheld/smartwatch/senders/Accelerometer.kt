package com.damnluck.sensordatatransporterhandheld.smartwatch.senders

import android.app.Activity
import com.google.android.gms.wearable.WearableListenerService

class Accelerometer(private val activity: Activity) : WearableListenerService() {
    fun startAccelerometer() {
        SendData(activity).sendToWatch("/startAccelerometer", "")
    }

    fun stopAccelerometer() {
        SendData(activity).sendToWatch("/stopAccelerometer", "")
    }
}