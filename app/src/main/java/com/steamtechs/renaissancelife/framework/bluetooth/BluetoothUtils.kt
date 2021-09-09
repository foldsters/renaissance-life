package com.steamtechs.renaissancelife.framework.bluetooth

import android.bluetooth.BluetoothDevice
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.util.*


val BluetoothUUID : UUID = UUID.fromString("fb5f7580-d51f-4e5d-b070-99edc1eaf1d4")

// Data class containing information about a message received from the server
data class ReceivedMessageData (
    val time: Long,
    val deviceAddress: String?,
    val message: String
)

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

typealias BluetoothMessageCallback = (BluetoothMessageResponseModel) -> Unit
