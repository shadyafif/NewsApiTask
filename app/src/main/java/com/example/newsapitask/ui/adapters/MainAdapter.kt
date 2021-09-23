package com.example.newsapitask.ui.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapitask.R
import com.example.newsapitask.data.model.NewsDetails.Article
import com.example.newsapitask.databinding.LayoutMainRecyclerBinding
import com.example.newsapitask.utilities.Extensions.loadImage
import java.util.*
import kotlin.collections.ArrayList

class MainAdapter(val context: Context, private val onClick: IItemClickListener) :
    RecyclerView.Adapter<MainAdapter.ViewHolder>(), Filterable {

    var newsList: ArrayList<Article> = ArrayList()
    var newsSearchList: ArrayList<Article> = ArrayList()

    @SuppressLint("NotifyDataSetChanged")
    fun setNewsList(newsLst: List<Article>) {
        newsList = newsLst as ArrayList<Article>
        newsSearchList = newsList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainAdapter.ViewHolder {
        val li = LayoutInflater.from(context)
        return ViewHolder(onClick, LayoutMainRecyclerBinding.inflate(li))
    }

    override fun onBindViewHolder(holder: MainAdapter.ViewHolder, position: Int) {
        val currentItem = newsSearchList[position]
        holder.bind(currentItem)

    }

    override fun getItemCount(): Int {
        return newsSearchList.size
    }

    inner class ViewHolder(
        private val mOnItemClickListener: IItemClickListener,
        private val binding: LayoutMainRecyclerBinding
    ) : RecyclerView.ViewHolder(binding.root), View.OnClickListener {
        var mItemClickListener: IItemClickListener? = null
        fun bind(news: Article) {
            binding.apply {
                if (news.urlToImage != null) {
                    ivNews.loadImage(context, news.urlToImage)
                } else {
                    ivNews.setImageResource(R.drawable.nophoto)
                }

                tvNewsTitle.text = news.title
                tvNewsSource.text = news.source!!.name
                tvNewsPublishedAt.text = news.publishedAt!!.substring(0, 10)
                mItemClickListener = mOnItemClickListener
                ivNews.setOnClickListener(this@ViewHolder)
            }
        }

        override fun onClick(v: View?) {
            val item = newsList[layoutPosition]
            mItemClickListener?.onItemClickListener(item)
        }

    }


    interface IItemClickListener {
        fun onItemClickListener(item: Article)
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charString = constraint?.toString() ?: ""
                newsSearchList = if (charString.isEmpty()) newsList else {
                    val filteredList = ArrayList<Article>()
                    newsList
                        .filter {
                            it.title.startsWith(charString, false)
                        }
                        .forEach { filteredList.add(it) }
                    filteredList

                }
                return FilterResults().apply { values = newsSearchList }


            }

            @SuppressLint("NotifyDataSetChanged")
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {

                newsSearchList = if (results?.values == null)
                    ArrayList()
                else
                    results.values as ArrayList<Article>
                notifyDataSetChanged()
            }
        }

    }
}


