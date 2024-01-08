package com.example.wz_tracker.util

object ModeNameUtil {
    fun getMatchMode(mode: String) = StringBuilder().apply {
        append(getMatchMap(mode))
        append(getMatchType(mode))
    }.toString()

    private fun getMatchMap(mode: String) =
        with(mode) {
            when {
                contains("rbrth") -> "Rebirth Island "
                contains("fortkeep") -> "Fortunes Keep "
                else -> "Caldera "
            }
        }

    private fun getMatchType(mode: String) =
        with(mode) {
            when {
                contains("duo") -> "Duos"
                contains("trio") -> "Trios"
                contains("quad") -> "Quads"
                contains("clash") -> "Clash"
                else -> "Unknown"
            }
        }
}