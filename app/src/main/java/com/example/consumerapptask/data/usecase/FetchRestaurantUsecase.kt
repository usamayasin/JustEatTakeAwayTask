package com.example.consumerapptask.data.usecase

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.consumerapptask.data.model.OpeningStatus
import com.example.consumerapptask.data.model.Restaurant
import com.example.consumerapptask.data.model.SortingOption
import com.example.consumerapptask.data.model.SortingOption.*
import com.example.consumerapptask.data.repository.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

@RequiresApi(Build.VERSION_CODES.N)
class FetchRestaurantUsecase @Inject constructor(
    private val repository: Repository,
) {

    @ExperimentalCoroutinesApi
    suspend operator fun invoke(currentSorting: Comparator<Restaurant>): Flow<List<Restaurant>> {
        return flow {
            repository.getRestaurants().collect {
                val restaurants = it.restaurants.sortedWith(getCurrentSorting(currentSorting))
                restaurants.map { item ->
                    item.selectedSort = SortingOption.BEST_MATCH.name.toString()
                }
                emit(restaurants)
            }
        }.flowOn(Dispatchers.IO)
    }


    fun getCurrentSorting(currentSorting: Comparator<Restaurant>): Comparator<Restaurant> =
        compareBy<Restaurant> { it.status == OpeningStatus.OPEN.value }
            .thenBy { it.status == OpeningStatus.ORDER_AHEAD.value }
            .thenBy { it.status == OpeningStatus.CLOSED.value }
            .then(currentSorting)
            .reversed()

    fun getComparatorBy(option: SortingOption) = when (option) {
        BEST_MATCH -> compareBy<Restaurant> { it.sortingValues?.bestMatch }
        NEWEST -> compareByDescending { it.sortingValues?.newest }
        RATING_AVERAGE -> compareBy { it.sortingValues?.ratingAverage }
        DISTANCE -> compareByDescending { it.sortingValues?.distance }
        POPULARITY -> compareBy { it.sortingValues?.popularity }
        AVERAGE_PRODUCT_PRICE -> compareByDescending { it.sortingValues?.averageProductPrice }
        DELIVERY_COSTS -> compareByDescending { it.sortingValues?.deliveryCosts }
        MIN_COST -> compareByDescending { it.sortingValues?.minCost }
        NAME -> compareByDescending { it.name }
    }

    fun searchByName(searchInput: CharSequence, restaurantsList: List<Restaurant>): List<Restaurant> {
        return restaurantsList.filter { item ->
            item.name!!.contains(searchInput, ignoreCase = true)
        }
    }
}

