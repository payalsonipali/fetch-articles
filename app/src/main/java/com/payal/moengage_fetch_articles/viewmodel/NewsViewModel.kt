package com.payal.moengage_fetch_articles.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.payal.moengage_fetch_articles.model.News
import com.payal.moengage_fetch_articles.model.NewsState
import com.payal.moengage_fetch_articles.repository.NewsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(private val newsRepository: NewsRepository) : ViewModel() {
    private val _news = MutableStateFlow<NewsState>(NewsState.Loading)
    val news: StateFlow<NewsState> = _news

    init {
        fetchNews()
    }

    private fun fetchNews() {
        viewModelScope.launch(Dispatchers.IO) {
            val result = newsRepository.fetchNews()
            result.onSuccess { newsList ->
                _news.value = NewsState.Success(newsList)
            }.onFailure { exception ->
                _news.value = NewsState.Error(exception.message ?: "Something went wrong")
            }
        }
    }

    private fun sortNewsByDate(newsList: List<News>, ascending: Boolean = true): List<News> {
        return newsList.sortedBy {
            it.publishedAt
        }.let {
            if (!ascending) it.reversed() else it
        }
    }

    fun sortNews(ascending: Boolean = true) {
        val currentNewsState = _news.value
        if (currentNewsState is NewsState.Success) {
            val sortedNews = sortNewsByDate(currentNewsState.news, ascending)
            _news.value = NewsState.Success(sortedNews)
        }
    }
}