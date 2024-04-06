package com.payal.moengage_fetch_articles.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.payal.moengage_fetch_articles.model.News
import com.payal.moengage_fetch_articles.repository.NewsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(private val newsRepository: NewsRepository) : ViewModel() {
    private val _news = MutableLiveData<List<News>>()
    val news: LiveData<List<News>> = _news

    init {
        Log.d("taggg", "000000")
        fetchNews()
    }

    private fun fetchNews() {
        Log.d("taggg", "111111111")

        viewModelScope.launch(Dispatchers.IO) {
            val newsList = newsRepository.fetchNews()
            _news.postValue(newsList)
        }
    }
}