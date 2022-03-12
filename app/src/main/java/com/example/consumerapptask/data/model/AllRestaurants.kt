package com.example.consumerapptask.data.model

import com.google.gson.annotations.SerializedName

data class AllRestaurants(
    @SerializedName("restaurants")
    var restaurants: ArrayList<Restaurant> = arrayListOf()
)

data class Restaurant(
    @SerializedName("id")
    var id: String? = null,
    @SerializedName("name")
    var name: String? = null,
    @SerializedName("status")
    var status: String? = null,
    @SerializedName("sortingValues")
    var sortingValues: SortingValues? = SortingValues(),

    var selectedSort:String
)

data class SortingValues(
    @SerializedName("bestMatch")
    var bestMatch: Int? = null,
    @SerializedName("newest")
    var newest: Int? = null,
    @SerializedName("ratingAverage")
    var ratingAverage: Double? = null,
    @SerializedName("distance")
    var distance: Int? = null,
    @SerializedName("popularity")
    var popularity: Int? = null,
    @SerializedName("averageProductPrice")
    var averageProductPrice: Int? = null,
    @SerializedName("deliveryCosts")
    var deliveryCosts: Int? = null,
    @SerializedName("minCost")
    var minCost: Int? = null
)