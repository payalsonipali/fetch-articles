package com.payal.moengage_fetch_articles.service

import com.payal.moengage_fetch_articles.model.News

interface NewsService {
    fun getNews(): List<News>
}