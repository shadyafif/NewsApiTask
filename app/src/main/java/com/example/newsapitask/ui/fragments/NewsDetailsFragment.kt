package com.example.newsapitask.ui.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.newsapitask.R
import com.example.newsapitask.data.model.NewsDetails.Article
import com.example.newsapitask.databinding.FragmentNewsDetailsBinding
import com.example.newsapitask.utilities.Extensions.loadImage


class NewsDetailsFragment : Fragment(), View.OnClickListener {
    private var _binding: FragmentNewsDetailsBinding? = null
    private val binding get() = _binding!!
    private lateinit var artical: Article

    companion object {
        fun newsDetailsFragment(article: Article?): NewsDetailsFragment {
            val newsDetailsFragment = NewsDetailsFragment()
            val bundle = Bundle()
            bundle.putParcelable("article", article)
            newsDetailsFragment.arguments = bundle
            return newsDetailsFragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        assert(arguments != null)
        artical = arguments?.getParcelable("article")!!
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNewsDetailsBinding.inflate(inflater, container, false)
        initView()
        return binding.root
    }

    private fun initView() {
        binding.tvTitle.text = artical.title
        if (artical.urlToImage == null) {
            binding.ivDetails.setImageResource(R.drawable.nophoto)
        } else {
            binding.ivDetails.loadImage(requireActivity(), artical.urlToImage!!)
        }
        binding.tvDescription.text = artical.description
        binding.tvSource.text = artical.source!!.name
        binding.ivShare.setOnClickListener(this)
    }

    override fun onClick(view: View) {
        if (view.id == R.id.iv_share) {
            val share = Intent(Intent.ACTION_SEND)
            share.type = "text/plain"
            share.putExtra(Intent.EXTRA_SUBJECT, artical.title)
            share.putExtra(Intent.EXTRA_TEXT, artical.url)
            startActivity(Intent.createChooser(share, "Share link!"))
        }
    }


}