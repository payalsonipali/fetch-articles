package com.payal.moengage_fetch_articles.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.payal.moengage_fetch_articles.R
import com.payal.moengage_fetch_articles.databinding.ArticleItemBinding
import com.payal.moengage_fetch_articles.model.News

class NewsAdapter(private val newsList: List<News>, private val onItemClick: (String) -> Unit) : RecyclerView.Adapter<NewsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ArticleItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val news = newsList[position]
        holder.bind(news)
        holder.itemView.setOnClickListener { onItemClick(news.url) }
    }

    override fun getItemCount(): Int = newsList.size

    class ViewHolder(private val binding: ArticleItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(news: News) {
            binding.news = news
            Glide.with(itemView)
                .load(news.urlToImage)
                .placeholder(R.drawable.ic_launcher_background)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(binding.img)
            binding.executePendingBindings()
        }
    }
}