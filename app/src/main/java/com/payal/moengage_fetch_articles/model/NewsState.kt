package com.payal.moengage_fetch_articles.model

sealed class NewsState {
    object Loading : NewsState()
    data class Success(val news: List<News>) : NewsState()
    data class Error(val message: String) : NewsState()
}
