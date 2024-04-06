package com.payal.moengage_fetch_articles.service

import com.payal.moengage_fetch_articles.model.News

interface NewsService {
    suspend fun getNews(): List<News>
}