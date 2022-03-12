package com.example.consumerapptask.data.service

import android.content.Context
import com.example.consumerapptask.data.model.AllRestaurants
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.IOException
import javax.inject.Inject

/**
 * This is an implementation of [Repository] to handle communication with [ApiService] server.
 */

class ApiServiceImpl @Inject constructor(
    private val context: Context,
) : ApiService {

    override suspend fun getAllRestaurants(): AllRestaurants = getAllRestaurantsFromJson(context)

    private fun getAllRestaurantsFromJson(context: Context): AllRestaurants {
        lateinit var jsonString: String
        try {
            jsonString = context.assets.open("sample_data.json")
                .bufferedReader()
                .use { it.readText() }
        } catch (ex: IOException) {
            ex.printStackTrace()
        }
        val listRestaurant = object : TypeToken<AllRestaurants>() {}.type
        return Gson().fromJson(jsonString, listRestaurant)
    }

}
