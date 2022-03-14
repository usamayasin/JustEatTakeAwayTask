package com.example.consumerapptask.utils

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.consumerapptask.data.model.SortingOption
import com.example.consumerapptask.data.model.SortingValues

@BindingAdapter(value = ["sortOption", "sortValue"])
fun selectedSortValue(view: TextView, option: String, sortValues: SortingValues) {
    when (option) {
        SortingOption.BEST_MATCH.name -> {
            view.text = sortValues.bestMatch.toString()
        }
        SortingOption.NEWEST.name -> {
            view.text = sortValues.newest.toString()
        }
        SortingOption.RATING_AVERAGE.name -> {
            view.text = sortValues.ratingAverage.toString()
        }
        SortingOption.DISTANCE.name -> {
            view.text = sortValues.distance.toString()
        }
        SortingOption.POPULARITY.name -> {
            view.text = sortValues.popularity.toString()
        }
        SortingOption.AVERAGE_PRODUCT_PRICE.name -> {
            view.text = sortValues.averageProductPrice.toString()
        }
        SortingOption.DELIVERY_COSTS.name -> {
            view.text = sortValues.deliveryCosts.toString()
        }
        SortingOption.MIN_COST.name -> {
            view.text = sortValues.minCost.toString()
        }
    }
}
