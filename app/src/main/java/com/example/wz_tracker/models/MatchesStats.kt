package com.example.wz_tracker.models

data class MatchesStats (
    val matches: List<WzMatch>
)

data class WzMatch (
    val utcStartSeconds: Long,
    val mode: String,
    val playerStats: PlayerStats
)

data class PlayerStats(
    val kills: Int,
    val headshots: Int,
    val deaths: Int,
    val gulagDeaths: Int,
    val gulagKills: Int,
    val teamPlacement: Int,
    val damageDone: Int,
    val damageTaken: Int,
)

