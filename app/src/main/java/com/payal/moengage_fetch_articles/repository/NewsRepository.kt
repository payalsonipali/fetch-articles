package com.payal.moengage_fetch_articles.repository

import com.payal.moengage_fetch_articles.model.News
import com.payal.moengage_fetch_articles.service.NewsService
import javax.inject.Inject

class NewsRepository @Inject constructor(private val newsService: NewsService) {
    fun fetchNews(): List<News> {
        return newsService.getNews()
    }
}