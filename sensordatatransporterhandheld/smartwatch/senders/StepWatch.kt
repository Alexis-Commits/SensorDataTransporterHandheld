package com.damnluck.sensordatatransporterhandheld.smartwatch.senders

import android.app.Activity
import com.google.android.gms.wearable.WearableListenerService

class StepWatch(private val activity: Activity) : WearableListenerService() {

    val stepCount: Unit
        get() {
            SendData(activity).sendToWatch("/getStepCount", "")
        }
}