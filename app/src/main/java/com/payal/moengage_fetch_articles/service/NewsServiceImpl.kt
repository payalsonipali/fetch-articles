package com.payal.moengage_fetch_articles.service

import com.google.gson.Gson
import com.payal.moengage_fetch_articles.model.News
import com.payal.moengage_fetch_articles.model.NewsResponse
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL
import javax.inject.Inject

class NewsServiceImpl @Inject constructor() : NewsService {
    @Throws(IOException::class)
    override suspend fun getNews(): List<News> {
        val url = URL("https://candidate-test-data-moengage.s3.amazonaws.com/Android/news-api-feed/staticResponse.json")
        val connection = url.openConnection() as HttpURLConnection
        connection.connect()

        if (connection.responseCode == HttpURLConnection.HTTP_OK) {
            val inputStream = connection.inputStream
            val json = inputStream.bufferedReader().use { it.readText() }
            val response = Gson().fromJson(json, NewsResponse::class.java)
            return response.articles
        } else {
            throw IOException("Failed to fetch news: HTTP ${connection.responseCode}")
        }
    }
}