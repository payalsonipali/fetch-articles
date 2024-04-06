package com.payal.moengage_fetch_articles.model

import java.text.SimpleDateFormat
import java.util.Locale

data class NewsResponse(
    val articles: List<News>
)

data class News(
    val source: Source,
    val author: String?,
    val title: String,
    val description: String?,
    val url: String,
    val urlToImage: String?,
    val publishedAt: String?,
    val content: String?
) {
    fun toNewsWithFormattedDate(): News {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
        val parsedDate = publishedAt?.let { dateFormat.parse(it) }
        val formattedDate = parsedDate?.let {
            SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(
                it
            )
        }
        return copy(publishedAt = formattedDate)
    }
}

data class Source(
    val id: String?,
    val name: String
)