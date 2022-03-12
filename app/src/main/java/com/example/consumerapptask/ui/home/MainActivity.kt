package com.example.consumerapptask.ui.home

import android.os.Build
import android.os.Bundle
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.lifecycle.Observer
import com.example.consumerapptask.adapter.RestaurantsAdapter
import com.example.consumerapptask.base.BaseActivity
import com.example.consumerapptask.data.model.Restaurant
import com.example.consumerapptask.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : BaseActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()

    private lateinit var restaurantsAdapter: RestaurantsAdapter


    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initListener()
        initObservations()
    }

    private fun initListener() {

        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        restaurantsAdapter = RestaurantsAdapter()
        binding.rvRestaurants.adapter = restaurantsAdapter
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun initObservations() {

        val restaurantObserver = Observer<List<Restaurant>> { response ->
            // Update the UI, in this case
            response?.let {
                if (response.isNotEmpty()) {
                    restaurantsAdapter.differ.submitList(response)
                }
            }
        }
        viewModel.restaurantsLiveData.observe(this, restaurantObserver)
    }

}