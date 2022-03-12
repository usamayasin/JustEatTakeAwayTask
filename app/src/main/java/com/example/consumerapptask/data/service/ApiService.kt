package com.example.consumerapptask.data.service

import com.example.consumerapptask.data.model.AllRestaurants

interface ApiService {
   suspend fun getAllRestaurants(): AllRestaurants
}
