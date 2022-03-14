package com.example.consumerapptask.ui.home

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.consumerapptask.base.BaseViewModel
import com.example.consumerapptask.data.DataState
import com.example.consumerapptask.data.model.Restaurant
import com.example.consumerapptask.data.model.SortingOption
import com.example.consumerapptask.data.usecase.FetchRestaurantUsecase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@RequiresApi(Build.VERSION_CODES.N)
@HiltViewModel
class MainViewModel @Inject constructor(
    private val fetchRestaurantUsecase: FetchRestaurantUsecase
) : BaseViewModel() {

    private var _restaurantList = MutableLiveData<List<Restaurant>>()
    var restaurantsLiveData: LiveData<List<Restaurant>> = _restaurantList

    private var currentSorting = fetchRestaurantUsecase.getComparatorBy(SortingOption.BEST_MATCH)

    var query = MutableStateFlow("")
    var restaurantListStateFlow = query.debounce(100)
        .distinctUntilChanged()
        .flatMapLatest {
            searchRestaurantByName(it)
        }.flowOn(Dispatchers.Default)

    init {
        fetchRestaurants()
    }

    private fun fetchRestaurants() {
        showLoader()
        viewModelScope.launch(Dispatchers.IO) {
            fetchRestaurantUsecase.invoke(currentSorting).collect { data ->
                withContext(Dispatchers.Main) {
                    hideLoading()
                    _restaurantList.postValue(data)
                }
            }
        }
    }

    fun searchRestaurantByName(searchInput: String): Flow<List<Restaurant>> = flow {
        if (searchInput.trim().isNotEmpty()) {
            _restaurantList.value?.let {
                val result = fetchRestaurantUsecase.searchByName(searchInput, it)
                if (result.isNullOrEmpty().not()) {
                    emit(result)
                } else {
                    onResponseComplete(DataState.CustomMessages.EmptyData)
                }
            }
        } else {
            fetchRestaurants()
        }
    }

    fun onSortingOptionChecked(option: SortingOption) {
        currentSorting = fetchRestaurantUsecase.getComparatorBy(option)
        _restaurantList.value?.let {
            val list = it.sortedWith(
                fetchRestaurantUsecase.getCurrentSorting(
                    currentSorting
                )
            )
            list.map {
                it.selectedSort = option.name
            }
            _restaurantList.postValue(list)
        }
    }
}

