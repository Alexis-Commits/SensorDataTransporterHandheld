package com.damnluck.sensordatatransporterhandheld.smartwatch.senders

import android.app.Activity
import android.util.Log
import com.google.android.gms.tasks.Tasks
import com.google.android.gms.wearable.Wearable
import java.util.concurrent.ExecutionException

class SendData(private val activity: Activity) {
    fun sendToWatch(path: String, data: String) {
        SendThread(path, data).start()
    }

    internal inner class SendThread(var path: String, var message: String) : Thread() {
        override fun run() {
            val nodeListTask = Wearable.getNodeClient(
                activity.applicationContext
            ).connectedNodes
            try {
                val nodes = Tasks.await(nodeListTask)
                for (node in nodes) {
                    val sendMessageTask = Wearable.getMessageClient(
                        activity.applicationContext
                    ).sendMessage(node.id, path, message.toByteArray())
                    try {
                        val result = Tasks.await(sendMessageTask)
                        Log.v(
                            "SensorDataTransporterHandheld",
                            "nodes number :" + nodes.size + " \t SendThread: message send to " + node.displayName + "\t\t" + path
                        )
                    } catch (exception: ExecutionException) {
                        Log.e("SensorDataTransporterHandheld", "Task failed: $exception")
                    } catch (exception: InterruptedException) {
                        Log.e("SensorDataTransporterHandheld", "Interrupt occurred: $exception")
                    }
                }
            } catch (exception: ExecutionException) {
                Log.e("SensorDataTransporterHandheld", "Task failed: $exception")
            } catch (exception: InterruptedException) {
                Log.e("SensorDataTransporterHandheld", "Interrupt occurred: $exception")
            }
        }
    }
}