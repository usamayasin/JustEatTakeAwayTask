package com.example.consumerapptask.utils

import android.content.Context
import androidx.appcompat.widget.SearchView
import com.example.consumerapptask.data.model.AllRestaurants
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.MutableStateFlow
import java.io.IOException

fun SearchView.getQueryTextChangeStateFlow(): MutableStateFlow<String> {

    val query = MutableStateFlow("")
    setOnQueryTextListener(object : SearchView.OnQueryTextListener {
        override fun onQueryTextSubmit(query: String?): Boolean {
            return true
        }
        override fun onQueryTextChange(newText: String): Boolean {
            query.value = newText
            return true
        }
    })
    return query
}

fun getAllRestaurantsFromJson(context: Context): AllRestaurants {
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
