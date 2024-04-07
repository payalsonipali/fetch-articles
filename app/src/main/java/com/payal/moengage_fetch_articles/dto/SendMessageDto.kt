package com.payal.moengage_fetch_articles.dto

data class SendMessageDto(
    val to : String?,
    val notificationBody: NotificationBody
)

data class NotificationBody(
    val title: String,
    val body: String
)