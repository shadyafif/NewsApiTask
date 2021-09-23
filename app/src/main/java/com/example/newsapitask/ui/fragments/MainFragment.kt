package com.example.newsapitask.ui.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import com.example.newsapitask.R
import com.example.newsapitask.data.model.NewsDetails.Article
import com.example.newsapitask.databinding.FragmentMainBinding
import com.example.newsapitask.ui.adapters.MainAdapter
import com.example.newsapitask.ui.fragments.NewsDetailsFragment.Companion.newsDetailsFragment
import com.example.newsapitask.ui.viewModels.MainViewModel
import com.example.newsapitask.utilities.Constants
import com.example.newsapitask.utilities.Extensions.addFragment
import com.example.newsapitask.utilities.Extensions.initRecyclerView
import com.example.newsapitask.utilities.Extensions.replaceFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainFragment : Fragment(), MainAdapter.IItemClickListener {
    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!
    private var adapter: MainAdapter? = null
    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        initViews()
        val viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        CoroutineScope(Dispatchers.IO).launch {
            viewModel.getAllNews(Constants.country, Constants.API_KEY)
        }
        viewModel.getDatumNesList().observe(requireActivity(), {
            adapter!!.setNewsList(it)
        })
        binding.svNewsSearch.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                adapter!!.filter.filter(query)
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                adapter!!.filter.filter(newText)
                return false
            }

        })
        return binding.root
    }

    private fun initViews() {
        adapter = MainAdapter(requireActivity(), this)
        initRecyclerView(requireContext(), binding.rvNews, adapter, 1)
    }

    override fun onItemClickListener(item: Article) {
        replaceFragment(newsDetailsFragment(item), R.id.frame_layout,requireActivity().supportFragmentManager.beginTransaction())
    }


}