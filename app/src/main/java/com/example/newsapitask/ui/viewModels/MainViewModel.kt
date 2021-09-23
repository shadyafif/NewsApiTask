package com.example.newsapitask.ui.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.newsapitask.data.model.NewsDetails.Article
import com.example.newsapitask.data.repository.MainRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repo: MainRepo) : ViewModel() {
    fun getDatumNesList(): MutableLiveData<List<Article>> {
        return repo.getNews()
    }

    suspend fun getAllNews(country: String, apiKey: String) {
        repo.getNewsListRepo(country, apiKey)
    }
}