package com.example.consumerapptask.data.model

enum class SortingOption {
    BEST_MATCH,
    NEWEST,
    RATING_AVERAGE,
    DISTANCE,
    POPULARITY,
    AVERAGE_PRODUCT_PRICE,
    DELIVERY_COSTS,
    NAME,
    MIN_COST;

    override fun toString(): String {
        return name
            .replace('_', ' ')
            .lowercase()
    }
}
