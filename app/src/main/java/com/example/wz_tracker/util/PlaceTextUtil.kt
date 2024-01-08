package com.example.wz_tracker.util

object PlaceTextUtil {
    private const val FIRST_PLACE = 1
    private const val SECOND_PLACE = 2
    private const val THIRD_PLACE = 3

    fun getPlaceOrdinalNumber(place: Int) = when (place) {
        FIRST_PLACE -> "1st"
        SECOND_PLACE -> "2nd"
        THIRD_PLACE -> "3rd"
        else -> place.toString() + "th"
    }
}
