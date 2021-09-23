package com.example.newsapitask.utilities

import android.content.Context
import android.view.View
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.newsapitask.R
import com.google.android.material.snackbar.Snackbar

object Extensions {

    fun replaceFragment(fragment: Fragment?, id: Int, fragmentTransaction: FragmentTransaction) {
        fragmentTransaction.replace(id, fragment!!)
        fragmentTransaction.addToBackStack(fragment.javaClass.name)
        fragmentTransaction.commit()

    }

    fun addFragment(fragment: Fragment?, id: Int,addToBackStack:Boolean, fragmentTransaction: FragmentTransaction) {
        fragmentTransaction.add(id, fragment!!)
        if (addToBackStack)
        fragmentTransaction.addToBackStack(fragment.javaClass.name)
        fragmentTransaction.commit()
    }

    fun showSnake(view: View, message: String) {
        Snackbar.make(view, message, Snackbar.LENGTH_SHORT).show()
    }

    fun ImageView.loadImage(context: Context,url: String) {
        Glide.with(context)
            .load(url)
            .apply(RequestOptions().placeholder(R.drawable.progress_animation))
            .into(this)
    }

    fun initRecyclerView(
        context: Context?,
        recyclerView: RecyclerView,
        adapter: RecyclerView.Adapter<*>?,
        column: Int
    ) {
        val layoutManager = GridLayoutManager(context, column)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter
    }
}