package com.example.consumerapptask.testUtil

import com.example.consumerapptask.data.model.AllRestaurants
import com.example.consumerapptask.data.model.Restaurant
import com.example.consumerapptask.data.model.SortingValues

class MockDataUtil {
    companion object {

        fun getMockRestaurants(): AllRestaurants {

            val sortingOption = SortingValues(
                bestMatch = 1,
                newest = 2,
                ratingAverage = 12.3,
                distance = 23,
                popularity = 2,
                averageProductPrice = 20,
                deliveryCosts = 23,
                minCost = 50)

            val restaurantsList: ArrayList<Restaurant> = arrayListOf()

            var item = Restaurant(
                id = "1",
                name = "Pakistani Kitchen",
                status = "open",
                sortingValues = sortingOption,
                selectedSort = "Best_Match"
            )
            restaurantsList.add(item)

            item = Restaurant(
                id = "2",
                name = "Indian Kitchen",
                status = "close",
                sortingValues = sortingOption,
                selectedSort = "Distance"
            )
            restaurantsList.add(item)

            item = Restaurant(
                id = "3",
                name = "English Kitchen",
                status = "Order Ahead",
                sortingValues = sortingOption,
                selectedSort = "Best_Match"
            )
            restaurantsList.add(item)

            return AllRestaurants(restaurants = restaurantsList)
        }
    }

}
