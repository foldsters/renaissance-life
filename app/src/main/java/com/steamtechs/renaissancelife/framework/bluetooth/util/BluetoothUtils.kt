package com.steamtechs.renaissancelife.framework.bluetooth.util

import android.bluetooth.BluetoothDevice
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.util.*


val BluetoothUUID : UUID = UUID.fromString("fb5f7580-d51f-4e5d-b070-99edc1eaf1d4")

@Serializable
data class BluetoothMessageRequestModel (
    val header : String?,
    val message : String,
)


data class BluetoothMessageResponseModel (
    val header : String?,
    val message : String,
    val deviceName : String?,
    val deviceAddress : String?
) {

    companion object {
        fun fromRequestModel(
            messageRequestModel: BluetoothMessageRequestModel,
            device: BluetoothDevice?) : BluetoothMessageResponseModel {

            messageRequestModel.apply {
                return BluetoothMessageResponseModel(header, message, device?.name, device?.address)
            }
        }
    }
}


fun encodeBluetoothMessageRequestModel(bluetoothMessageRequestModel: BluetoothMessageRequestModel): String {
    return Json.encodeToString(bluetoothMessageRequestModel)
}


fun decodeBluetoothMessageRequestString(bluetoothMessageResponseString: String): BluetoothMessageRequestModel {
    return Json.decodeFromString(bluetoothMessageResponseString)
}


// Broadcast Receiver to run a callback whenever a new device is connected
fun bluetoothBroadcastReceiver(callback : (BluetoothDevice) -> Unit) = object : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val action = intent.action
        // When discovery finds a device
        if (BluetoothDevice.ACTION_FOUND == action) {
            // Get the BluetoothDevice object from the Intent
            val device =
                intent.getParcelableExtra<BluetoothDevice>(BluetoothDevice.EXTRA_DEVICE)
                    ?: return

            callback(device)
        }
    }
}

typealias BluetoothMessageCallback = (BluetoothMessageResponseModel) -> Unit

typealias Callback<K> = (K) -> Unit


