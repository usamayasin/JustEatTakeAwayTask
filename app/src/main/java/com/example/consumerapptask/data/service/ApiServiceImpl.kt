package com.example.consumerapptask.data.service

import android.content.Context
import com.example.consumerapptask.data.model.AllRestaurants
import com.example.consumerapptask.utils.getAllRestaurantsFromJson
import javax.inject.Inject

/**
 * This is an implementation of [ApiService] to get data from local/server.
 */

class ApiServiceImpl @Inject constructor(
    private val context: Context,
) : ApiService {

    override suspend fun getAllRestaurants(): AllRestaurants = getAllRestaurantsFromJson(context)

}
