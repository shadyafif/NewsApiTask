package com.example.newsapitask.ui.activities


import am.networkconnectivity.NetworkConnectivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.newsapitask.R
import com.example.newsapitask.databinding.ActivityMainBinding
import com.example.newsapitask.ui.fragments.MainFragment
import com.example.newsapitask.utilities.Extensions.addFragment
import com.example.newsapitask.utilities.Extensions.showSnake
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
open class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val mainFragment = MainFragment()
        addFragment(mainFragment, R.id.frame_layout,false, supportFragmentManager.beginTransaction())

        NetworkConnectivity(this).observe(this, { onNetworkConnectionChanged(it) })


    }

    open fun onNetworkConnectionChanged(status: NetworkConnectivity.NetworkStatus) {
        when (status) {
            NetworkConnectivity.NetworkStatus.OnConnected -> {
                showSnake(binding.cL, this.getString(R.string.isConnected))
            }
            NetworkConnectivity.NetworkStatus.OnWaiting -> {
                showSnake(binding.cL,this.getString(R.string.isWaiting))
            }
            NetworkConnectivity.NetworkStatus.OnLost -> {
                showSnake(binding.cL,this.getString(R.string.isLost))
            }
        }
    }

}