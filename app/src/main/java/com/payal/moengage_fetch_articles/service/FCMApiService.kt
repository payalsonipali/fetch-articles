package com.payal.moengage_fetch_articles.service

import com.payal.moengage_fetch_articles.dto.SendMessageDto

interface FCMApiService {

    suspend fun sendMessage(
        body: SendMessageDto
    ) : String

    suspend fun broadcast(
        body: SendMessageDto
    ) : String
}