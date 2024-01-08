package com.example.wz_tracker.repositories

import com.example.wz_tracker.database.Match
import com.example.wz_tracker.models.LifetimeStats

interface WzRepository {
    suspend fun fetchWzMatches(username: String, numbers: String): List<Match>?
    suspend fun fetchWzLifetimeStats(username: String, numbers: String): LifetimeStats?
    fun getMatches(): List<Match>
    fun getWzMatchByID(id: Int): Match?
}