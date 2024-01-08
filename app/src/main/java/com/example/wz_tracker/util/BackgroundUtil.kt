package com.example.wz_tracker.util

import com.example.wz_tracker.R
import com.example.wz_tracker.database.Match

object BackgroundUtil {
    private const val LIFETIME_GOATED_KD = 2
    private const val LIFETIME_AMAZING_KD = 1.5
    private const val LIFETIME_GOOD_KD = 1
    private const val LIFETIME_AVERAGE_KD = 0.8

    private const val MATCH_GOATED_KD = 5
    private const val MATCH_AMAZING_KD = 3
    private const val MATCH_NEUTRAL_KD = 1

    private const val FIRST_PLACE = 1
    private const val SECOND_PLACE = 2
    private const val THIRD_PLACE = 3

    fun getLifetimeWinsBackground(wins: Int) = when {
        wins > 25 -> R.drawable.radial_gradient_goated_background
        wins > 15 -> R.drawable.radial_gradient_amazing_background
        wins > 10 -> R.drawable.radial_gradient_first_place_background
        wins > 5 -> R.drawable.radial_gradient_second_place_background
        wins > 0 -> R.drawable.radial_gradient_third_place_background
        else -> R.drawable.radial_gradient_other_place_background
    }

    fun getLifetimeKdBackground(kd: Float) = when {
        kd >= LIFETIME_GOATED_KD -> R.drawable.radial_gradient_goated_background
        kd >= LIFETIME_AMAZING_KD -> R.drawable.radial_gradient_amazing_background
        kd >= LIFETIME_GOOD_KD -> R.drawable.radial_gradient_first_place_background
        kd >= LIFETIME_AVERAGE_KD -> R.drawable.radial_gradient_second_place_background
        else -> R.drawable.radial_gradient_third_place_background
    }

    fun getCardBackgroundBasedOnPlace(place: Int) = when (place) {
        FIRST_PLACE -> R.drawable.lineat_gradient_first_place_background
        SECOND_PLACE -> R.drawable.linear_gradient_second_place_background
        THIRD_PLACE -> R.drawable.linear_gradient_third_place_background
        else -> R.drawable.linear_gradient_other_place_background
    }

    fun getMatchPlaceBackground(place: Int) = when (place) {
        1 -> R.drawable.radial_gradient_first_place_background
        2 -> R.drawable.radial_gradient_second_place_background
        3 -> R.drawable.radial_gradient_third_place_background
        else -> R.drawable.radial_gradient_other_place_background
    }

    fun getMatchKdBackground(kd: Float, match: Match) = when {
        hasZeroKillsAndDeaths(match) -> R.drawable.radial_gradient_other_place_background
        kd >= MATCH_GOATED_KD -> R.drawable.radial_gradient_goated_background
        kd >= MATCH_AMAZING_KD -> R.drawable.radial_gradient_amazing_background
        kd > MATCH_NEUTRAL_KD -> R.drawable.radial_gradient_positive_background
        kd < MATCH_NEUTRAL_KD -> R.drawable.radial_gradient_negative_background
        else -> R.drawable.radial_gradient_second_place_background
    }

    private fun hasZeroKillsAndDeaths(match: Match) = match.kills == 0 && match.deaths == 0
}