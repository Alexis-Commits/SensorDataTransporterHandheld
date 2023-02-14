package com.damnluck.sensordatatransporterhandheld

import android.app.Activity
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.AsyncTask
import android.util.Log
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.damnluck.sensordatatransporterhandheld.model.XYZ
import com.damnluck.sensordatatransporterhandheld.smartwatch.SensorReceiver
import com.damnluck.sensordatatransporterhandheld.smartwatch.senders.Accelerometer
import com.damnluck.sensordatatransporterhandheld.smartwatch.senders.Gyroscope
import com.damnluck.sensordatatransporterhandheld.smartwatch.senders.HeartRate
import com.damnluck.sensordatatransporterhandheld.smartwatch.senders.StepWatch
import com.google.android.gms.tasks.Tasks
import com.google.android.gms.wearable.Wearable
import java.util.*
import java.util.concurrent.ExecutionException

class SensorDataTransporterHandheld(activity: Activity?) {
    //Context
    private var activity: Activity? = null

    //Heart Rate From Smart Watch
    private var heartrate: HeartRate? = null

    //Accelerometer from Smart Watch
    private var accelerometer: Accelerometer? = null

    //Gyroscope from Smart Watch
    private var gyroscope: Gyroscope? = null

    //Steps from Smart Watch
    private var stepWatch: StepWatch? = null

    /**
     * @param activity current activity
     */
    init {

        //context
        this.activity = activity

        // Register the local broadcast receiver to get data from watch
        val messageFilter = IntentFilter(Intent.ACTION_SEND)
        val messageReceiver = MessageReceiver()
        LocalBroadcastManager.getInstance(activity!!)
            .registerReceiver(messageReceiver, messageFilter)
    }

    //Check if the phone is paired with the watch
    class checkIfConnected(var isOkCallbackWatch: isOkCallbackWatch, var context: Context?) :
        AsyncTask<Void?, Void?, Void?>() {
        fun run() {
            val nodeListTask = Wearable.getNodeClient(
                context!!
            ).connectedNodes
            try {
                val nodes = Tasks.await(nodeListTask)
                if (nodes.size > 0) {
                    isOkCallbackWatch.OK(nodes[0].displayName)
                } else {
                    isOkCallbackWatch.notOK()
                }
            } catch (exception: ExecutionException) {
                Log.e("SensorDataTransporterHandheld", "Task failed: $exception")
                isOkCallbackWatch.notOK()
            } catch (exception: InterruptedException) {
                Log.e("SensorDataTransporterHandheld", "Interrupt occurred: $exception")
                isOkCallbackWatch.notOK()
            } catch (e: Exception) {
                isOkCallbackWatch.notOK()
            }
        }

        override fun doInBackground(vararg voids: Void?): Void? {
            run()
            return null
        }
    }

    //This is the method which get called from the app ,you have to @Override the callback
    fun isWatchConnected(isOkCallbackWatch: isOkCallbackWatch) {
        checkIfConnected(isOkCallbackWatch, activity).execute()
    }

    //setup a broadcast receiver to receive the messages from the wear device via the listenerService.
    inner class MessageReceiver : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            val dataType = intent.getIntExtra("DATA_TYPE", 0)
            val message = intent.getStringExtra("message")
            if (message != null && !message.isEmpty()) {
                if (dataType == SensorReceiver.DATA_TYPE_HEART_RATE) {
                    val heartRate = java.lang.Double.valueOf(message)
                    sensorListenerHeartRate.onData(heartRate)
                } else if (dataType == SensorReceiver.DATA_TYPE_STEPS_COUNT) {
                    val steps = java.lang.Double.valueOf(message)
                    stepCounterDataListener.onData(steps.toInt())
                } else {
                    val result = listOf(*message.split("\\s*,\\s*".toRegex()).toTypedArray())
                    val xyz = XYZ(result[0].toFloat(), result[1].toFloat(), result[2].toFloat())
                    sensorListener.onData(xyz, dataType)
                }
            }
        }
    }

    fun startStopHeartRate(start: Boolean) {
        if (start) {
            heartrate!!.startHeartRate()
        } else {
            heartrate!!.stopHeartRate()
        }
    }

    fun startStopAccelerometer(start: Boolean) {
        if (start) {
            accelerometer!!.startAccelerometer()
        } else {
            accelerometer!!.stopAccelerometer()
        }
    }

    fun startStopGyroscope(start: Boolean) {
        if (start) {
            gyroscope!!.startGyroscope()
        } else {
            gyroscope!!.stopGyroscope()
        }
    }

    val stepCountSmartWatch: Unit
        get() {
            stepWatch!!.stepCount
        }

    fun smartWatchInit() {
        heartrate = activity?.let { HeartRate(it) }
        gyroscope = activity?.let { Gyroscope(it) }
        accelerometer = activity?.let { Accelerometer(it) }
        stepWatch = activity?.let { StepWatch(it) }
    }

    //Initialize listener
    var sensorListener: SensorListener = object : SensorListener {
        override fun onData(xyz: XYZ?, dataType: Int) {}
    }

    interface SensorListener {
        fun onData(xyz: XYZ?, dataType: Int)
    }

    //Initialize listener for heart rate
    var sensorListenerHeartRate: SensorListenerHeartRate = object : SensorListenerHeartRate {
        override fun onData(heartRate: Double) {}
    }
    var stepCounterDataListener: StepCounterDataListener = object : StepCounterDataListener {
        override fun onData(steps: Int) {}
    }

    interface StepCounterDataListener {
        fun onData(steps: Int)
    }

    interface SensorListenerHeartRate {
        fun onData(heartRate: Double)
    }

    interface isOkCallbackWatch {
        fun OK(displayName: String?)
        fun notOK()
    }
}