package com.example.consumerapptask.ui.home

import android.os.Build
import android.os.Bundle
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import com.example.consumerapptask.adapter.RestaurantsAdapter
import com.example.consumerapptask.base.BaseActivity
import com.example.consumerapptask.databinding.ActivityMainBinding
import com.example.consumerapptask.utils.flowWithLifecycle
import com.example.consumerapptask.utils.getQueryTextChangeStateFlow
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


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

        lifecycleScope.launch {
            launch {
                viewModel.restaurantListStateFlow.flowWithLifecycle(
                    lifecycle,
                    Lifecycle.State.STARTED
                )
                    .collect {
                        restaurantsAdapter.differ.submitList(it)
                    }
            }

            launch {
                binding.searchView.getQueryTextChangeStateFlow().collect {
                    viewModel.query.emit(it)
                }
            }

            launch {
                viewModel.responseMessage.flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
                    .collect {
                        showSnackbar(it,binding.root)
                    }
            }
        }

        viewModel.restaurantsLiveData.observe(this) { response ->
            // Update the UI, in this case
            response?.let {
                if (response.isNotEmpty()) {
                    restaurantsAdapter.differ.submitList(response)
                }
            }
        }
    }

}