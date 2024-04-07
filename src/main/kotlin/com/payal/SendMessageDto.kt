package com.payal

import com.google.firebase.messaging.Message
import com.google.firebase.messaging.Notification
import kotlinx.serialization.Serializable

@Serializable
data class SendMessageDto(
    val to : String?,
    val notificationBody: NotificationBody
)

@Serializable
data class NotificationBody(
    val title: String,
    val body: String
)

fun SendMessageDto.toMessage(): Message {
    return Message.builder()
        .setNotification(
            Notification.builder()
                .setTitle(notificationBody.title)
                .setBody(notificationBody.body)
                .build()
        )
        .apply {
            if(to == null) {
                // Send notification to all users
                setTopic("articles")
            } else {
                // Send notification to particular user
                setToken(to)
            }
        }
        .build()
}