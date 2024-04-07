package com.payal.moengage_fetch_articles.di

import com.payal.moengage_fetch_articles.repository.NewsRepository
import com.payal.moengage_fetch_articles.service.FCMApiService
import com.payal.moengage_fetch_articles.service.FCMApiServiceImpl
import com.payal.moengage_fetch_articles.service.NewsService
import com.payal.moengage_fetch_articles.service.NewsServiceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    fun provideNewsService(): NewsService {
        return NewsServiceImpl()
    }

    @Provides
    fun provideNewsRepository(newsService: NewsService): NewsRepository {
        return NewsRepository(newsService)
    }

    @Provides
    fun provideFCMService(): FCMApiService {
        return FCMApiServiceImpl()
    }
}