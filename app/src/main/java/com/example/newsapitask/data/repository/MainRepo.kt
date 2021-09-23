package com.example.newsapitask.data.repository

import androidx.lifecycle.MutableLiveData
import com.example.newsapitask.data.model.NewsDetails.Article
import com.example.newsapitask.data.network.IRetrofitApi
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MainRepo @Inject constructor(private var api: IRetrofitApi) {
    var newsList: MutableLiveData<List<Article>> = MutableLiveData<List<Article>>()
    var newsLst: List<Article>? = null

    fun getNews(): MutableLiveData<List<Article>> {
        return newsList
    }

    suspend fun getNewsListRepo(country:String,apiKey:String) {
        newsLst = api.getHeadlines(country,apiKey).articles
        newsList.postValue(newsLst)
    }
}