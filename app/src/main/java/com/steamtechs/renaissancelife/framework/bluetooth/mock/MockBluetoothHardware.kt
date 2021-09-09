package com.steamtechs.renaissancelife.framework.bluetooth.mock

import android.bluetooth.BluetoothDevice

object MockBluetoothHardware {

    private var streamData : Pair<BluetoothDevice?, String>? = null

    fun write(device : BluetoothDevice?, messageRequestString : String) {
        streamData = Pair(device, messageRequestString)
    }

    fun read() : Pair<BluetoothDevice?, String> {
        val result = streamData!!.copy()
        streamData = null
        return result
    }

    fun available() : Int {

        val available = streamData?.second?.length ?: 0
        return available

    }

}