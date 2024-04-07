package com.payal.moengage_fetch_articles.service

import com.google.gson.Gson
import com.payal.moengage_fetch_articles.dto.SendMessageDto
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL

class FCMApiServiceImpl : FCMApiService {

    //send notification to particular user
    @Throws(IOException::class)
    override suspend fun sendMessage(body: SendMessageDto): String {
        val url = URL("http://192.168.253.73:8080/send")
        try {
            val connection = url.openConnection() as HttpURLConnection
            connection.requestMethod = "POST"
            connection.setRequestProperty("Content-Type", "application/json")
            connection.doOutput = true
            val jsonBody = Gson().toJson(body)
            val outputStream = connection.outputStream
            outputStream.write(jsonBody.toByteArray())
            outputStream.flush()
            outputStream.close()
            connection.connect()

            if (connection.responseCode == HttpURLConnection.HTTP_OK) {
                return "Message sent successfully"
            } else {
                throw IOException("Failed to send message: HTTP ${connection.responseCode}")
            }
        } catch (e: Exception) {
            throw Exception("Something went wrong : ${e.message}")
        }
    }

    //send message to all users
    override suspend fun broadcast(body: SendMessageDto) : String {
        val url = URL("http://192.168.253.73:8080/broadcast")
        try {
            val connection = url.openConnection() as HttpURLConnection
            connection.requestMethod = "POST"
            connection.setRequestProperty("Content-Type", "application/json")
            connection.doOutput = true
            val jsonBody = Gson().toJson(body)
            val outputStream = connection.outputStream
            outputStream.write(jsonBody.toByteArray())
            outputStream.flush()
            outputStream.close()
            connection.connect()

            if (connection.responseCode == HttpURLConnection.HTTP_OK) {
                return "Message sent successfully"
            } else {
                throw IOException("Failed to send message: HTTP ${connection.responseCode}")
            }
        } catch (e: Exception) {
            throw Exception("Something went wrong : ${e.message}")
        }
    }
}
