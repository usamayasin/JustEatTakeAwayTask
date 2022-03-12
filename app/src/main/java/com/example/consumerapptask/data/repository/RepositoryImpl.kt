package com.example.consumerapptask.data.repository

import com.example.consumerapptask.data.model.AllRestaurants
import com.example.consumerapptask.data.service.ApiService
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

/**
 * This is an implementation of [Repository] to handle communication with [ApiService] server.
 */
class RepositoryImpl @Inject constructor(
    private val apiService: ApiService,
) : Repository {

    @OptIn(ExperimentalCoroutinesApi::class)
    override suspend fun getRestaurants(): Flow<AllRestaurants> = flow {
        emit(apiService.getAllRestaurants())
    }


}
