package com.payal.moengage_fetch_articles

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.ContextMenu
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import com.payal.moengage_fetch_articles.adapter.NewsAdapter
import com.payal.moengage_fetch_articles.databinding.ActivityMainBinding
import com.payal.moengage_fetch_articles.model.NewsState
import com.payal.moengage_fetch_articles.viewmodel.NewsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: NewsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        observeViewModel()
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            viewModel.news.collect { newState ->
                when (newState) {
                    is NewsState.Loading -> {
                        binding.loader.visibility = View.VISIBLE
                    }
                    is NewsState.Success -> {
                        binding.loader.visibility = View.GONE
                        binding.recycle.adapter = NewsAdapter(newState.news) { url ->
                            openWebPage(url)
                        }
                    }
                    is NewsState.Error -> {
                        binding.loader.visibility = View.GONE
                        Toast.makeText(this@MainActivity, newState.message, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    private fun openWebPage(url: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(intent)
    }

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_sort_newest -> {
                viewModel.sortNews(false)
                true
            }
            R.id.action_sort_oldest -> {
                viewModel.sortNews(true)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}