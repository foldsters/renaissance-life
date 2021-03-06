package com.steamtechs.renaissancelife.framework.bluetooth.real


import android.bluetooth.BluetoothDevice
import android.util.Log
import com.steamtechs.renaissancelife.framework.bluetooth.BluetoothUUID
import com.steamtechs.renaissancelife.framework.bluetooth.templates.BluetoothClient
import java.io.BufferedOutputStream
import java.lang.Exception
import java.lang.IllegalArgumentException

class RealBluetoothClient(device: BluetoothDevice?, private val message : String): BluetoothClient() {

    private val socket = device?.createRfcommSocketToServiceRecord(BluetoothUUID) ?:
        throw IllegalArgumentException("Real Bluetooth Client Device must not be null")

    private val tag = "client"

    override fun run() {
        Log.i(tag, "Connecting")

        // Try to connect to the server
        try {
            Log.i(tag, "Socket Connected? ${socket.isConnected}")
            socket.connect()
        } catch(e : Exception) {
            Log.e(tag, "Cannot Connect")
            return
        }

        Log.i(tag, "Sending: $message")


        val outputStream = socket.outputStream
        val inputStream = socket.inputStream

        val bufferedOutputStream = BufferedOutputStream(outputStream)

        // Try sending the message to the server on the other device
        // Doing so will cause that server to accept its server socket
        try {
            bufferedOutputStream.write(message.toByteArray())
            bufferedOutputStream.flush()
            Log.i(tag, "Sent")
        } catch(e: Exception) {
            Log.e(tag, "Cannot send", e)
        } finally {
            outputStream.close()
            inputStream.close()
            socket.close()
        }
    }
}