package com.example.consumerapptask.data.repository

import com.example.consumerapptask.data.model.AllRestaurants
import kotlinx.coroutines.flow.Flow

/**
 * Repository is an interface data layer to handle communication with any data source such as Server or local database.
 * @see [RepositoryImpl] for implementation of this class to utilize APIService.
 */

interface Repository {
    suspend fun getRestaurants(): Flow<AllRestaurants>
}
