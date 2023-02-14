
# SensorDataTransporterHandheld

This is a library that pairs with [SensorDataTransporterWearable](https://github.com/Alexis-Commits/SensorDataTransporterWearable) to achieve a connection between an `Android Mobile Device` with the `SmartWatch Device`


#### Important: ***This library is for the handheld  app***

## How to use it


Initialize the helper

```kotlin
val sensorDataTransporter = SensorDataTransporterHandheld(applicationContext)
```

***

### Then, Initialize smartwatch controllers


```kotlin
sensorDataTransporter.smartWatchInit()
```
***

### Now you are ready to start/stop smartwatch sensors

boolean: true -> Starts the smartwatch sensor and start receiving.

boolean: false -> Stops the smartwatch sensor.

#### Accelerometer

```kotlin
sensorDataTransporter.startStopAccelerometer(boolean)
```

***

#### Gyroscope

```kotlin
sensorDataTransporter.startStopGyroscope(boolean)
```

***


#### Heartrate

```kotlin
sensorDataTransporter.startStopHeartRate(boolean)
```

***


#### Step count

It just triggers the smartwatch to send the current step count.

```kotlin
sensorDataTransporter.stepCountSmartWatch
```

***


### To receive the sensor data use


#### For `Accelerometer and Gyroscope`

```kotlin
sensorDataTransporterHandheld.sensorListener =
            object : SensorDataTransporterHandheld.SensorListener {
                override fun onData(xyz: XYZ?, dataType: Int) {
                    if (dataType == SensorReceiver.DATA_TYPE_ACCELEROMETER) {
                        // Do something with the xyz object
                    } else {
                        // Do something with the xyz object
                    }
                }
            }

```

***


#### For the `Heartrate`

```kotlin
sensorDataTransporterHandheld.sensorListenerHeartRate = 
            object : SensorDataTransporterHandheld.SensorListenerHeartRate {
                override fun onData(heartRate: Double) {
                    // Do something with the hearRate value.                    
                }
            }
```
***

#### For the `Step Count`

```kotlin
 sensorDataTransporterHandheld.stepCounterDataListener = 
            object : SensorDataTransporterHandheld.StepCounterDataListener {
                override fun onData(steps: Int) {
                    // Do something with the steps value.
                }
            }
```
***

#### Also is available `isWatchConnected` method to check if the handheld is connected with the wearable

```kotlin
sensorDataTransporterHandheld.isWatchConnected(object :
            SensorDataTransporterHandheld.isOkCallbackWatch {
            override fun OK(displayName: String?) {
                // Do something with the displaName value
            }

            override fun notOK() {
               // Inform user that the device is not connected with the smartwatch
            }
        })

```

***


## Important things

 - The `handheld` app and the `smartwatch` app must have the same `package` name and must be signed with the same `keystore`

- You have to add this service inside `<application> .. </application>` tag

```xml
        <service
            android:name="com.damnluck.sensordatatransporterhandheld.smartwatch.SensorReceiver"
            android:enabled="true"
            android:exported="true" >
            <intent-filter>
                <action android:name="com.google.android.gms.wearable.DATA_CHANGED" />
                <action android:name="com.google.android.gms.wearable.MESSAGE_RECEIVED" />
                <data android:scheme="wear" android:host="*" android:pathPrefix="/dataAccelerometer" />
                <data android:scheme="wear" android:host="*" android:pathPrefix="/dataGyroscope" />
                <data android:scheme="wear" android:host="*" android:pathPrefix="/heartrateData" />
                <data android:scheme="wear" android:host="*" android:pathPrefix="/stepCount" />
            </intent-filter>
        </service>
```

- You have to add permissions in manifest

```xml
    <uses-permission android:name="android.permission.BLUETOOTH_ADMIN"/>
    <uses-permission android:name="android.permission.BLUETOOTH" />
    <uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION" />
    <uses-permission android:name="android.permission.ACCESS_FINE_LOCATION" />
    <uses-permission android:name="android.permission.BODY_SENSORS"/>
    <uses-permission android:name="android.permission.ACTIVITY_RECOGNITION"/>
```

- Also, you have add important dependencies in your gradle file

```groovy
    implementation 'com.google.android.gms:play-services-fitness:18.0.0'
    implementation 'com.google.android.gms:play-services-auth:17.0.0'
    implementation 'com.google.android.gms:play-services-wearable:15.0.0'
    implementation 'androidx.localbroadcastmanager:localbroadcastmanager:1.0.0'
    implementation 'com.google.code.gson:gson:2.8.6'

```

***

## The end


Now the app is ready to receive messages from the handheld to start sending sensor data.
