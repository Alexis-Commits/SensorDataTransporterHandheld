package com.damnluck.sensordatatransporterhandheld.model

/**
 * Helper object for accelerometer data and gyroscope data
 * */
class XYZ(val x: Float, val y: Float, val z: Float) {

    override fun toString(): String {
        return "XYZ{" +
                "x=" + x +
                ", y=" + y +
                ", z=" + z +
                '}'
    }
}