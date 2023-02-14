package com.damnluck.sensordatatransporterhandheld.smartwatch

import android.content.Intent
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.google.android.gms.wearable.MessageEvent
import com.google.android.gms.wearable.WearableListenerService

//Service to listen messages from watch
class SensorReceiver : WearableListenerService() {
    override fun onMessageReceived(messageEvent: MessageEvent) {
        if (messageEvent.path == "/dataAccelerometer") { //If it's accelerometer data
            val message = String(messageEvent.data)

            //Prepare Intent
            val messageIntent = Intent()
            messageIntent.action = Intent.ACTION_SEND
            messageIntent.putExtra("message", message)
            messageIntent.putExtra("DATA_TYPE", DATA_TYPE_ACCELEROMETER)


            //Sending message at broadcastReceiver
            LocalBroadcastManager.getInstance(this).sendBroadcast(messageIntent)
        } else if (messageEvent.path == "/dataGyroscope") { //If it's gyroscope data
            val message = String(messageEvent.data)

            //Prepare Intent
            val messageIntent = Intent()
            messageIntent.action = Intent.ACTION_SEND
            messageIntent.putExtra("message", message)
            messageIntent.putExtra("DATA_TYPE", DATA_TYPE_GYROSCOPE)

            //Sending message at broadcastReceiver
            LocalBroadcastManager.getInstance(this).sendBroadcast(messageIntent)
        } else if (messageEvent.path == "/heartRateData") {
            val message = String(messageEvent.data)
            //Prepare Intent
            val messageIntent = Intent()
            messageIntent.action = Intent.ACTION_SEND
            messageIntent.putExtra("message", message)
            messageIntent.putExtra("DATA_TYPE", DATA_TYPE_HEART_RATE)

            //Sending message at broadcastReceiver
            LocalBroadcastManager.getInstance(this).sendBroadcast(messageIntent)
        } else if (messageEvent.path == "/stepCount") {
            val message = String(messageEvent.data)
            //Prepare Intent
            val messageIntent = Intent()
            messageIntent.action = Intent.ACTION_SEND
            messageIntent.putExtra("message", message)
            messageIntent.putExtra("DATA_TYPE", DATA_TYPE_STEPS_COUNT)


            //Sending message at broadcastReceiver
            LocalBroadcastManager.getInstance(this).sendBroadcast(messageIntent)
        } else {
            super.onMessageReceived(messageEvent)
        }
    }

    companion object {
        var DATA_TYPE_ACCELEROMETER = 1
        var DATA_TYPE_GYROSCOPE = 2
        var DATA_TYPE_HEART_RATE = 3
        var DATA_TYPE_STEPS_COUNT = 4
    }
}