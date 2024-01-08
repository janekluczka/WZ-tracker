package com.example.wz_tracker.repositories

import com.example.wz_tracker.database.Match
import com.example.wz_tracker.database.WzDao
import com.example.wz_tracker.models.LifetimeStats
import com.example.wz_tracker.network.MwApiService
import com.example.wz_tracker.models.MatchesStats
import com.example.wz_tracker.models.WzMatch
import kotlin.Exception

class WzRepositoryImpl(private val database: WzDao) : WzRepository {

    override suspend fun fetchWzMatches(username: String, numbers: String) =
        try {
            val matches = MwApiService
                .retrofitService
                .fetchWzMatches(mergeUsernameAndNumbers(username, numbers))
            clearMatchesInDatabase()
            saveMatches(matches)
            database.getAllMatches()
        } catch (e: Exception) {
            val stackTrace = e.stackTrace
            null
        }

    private fun clearMatchesInDatabase() = database.deleteAllMatches()

    private fun saveMatches(matches: MatchesStats) {
        for ((index, match: WzMatch) in matches.matches.withIndex()) {
            insertMatchIntoDatabase(index, match)
        }
    }

    private fun insertMatchIntoDatabase(index: Int, wzMatch: WzMatch) =
        database.insertMatch(createMatch(index, wzMatch))

    private fun createMatch(index: Int, wzMatch: WzMatch) =
        Match(
            index,
            wzMatch.playerStats.teamPlacement,
            wzMatch.playerStats.kills,
            wzMatch.playerStats.deaths,
            wzMatch.playerStats.damageDone,
            wzMatch.playerStats.damageTaken,
            wzMatch.utcStartSeconds,
            wzMatch.mode
        )

    override suspend fun fetchWzLifetimeStats(username: String, numbers: String): LifetimeStats? =
        try {
            // handle saving response
            MwApiService
                .retrofitService
                .fetchWzLifetimeStats(mergeUsernameAndNumbers(username, numbers))
        } catch (e: Exception) {
            null
        }

    private fun mergeUsernameAndNumbers(username: String, numbers: String) = "$username%23$numbers"

    override fun getMatches(): List<Match> = database.getAllMatches()

    override fun getWzMatchByID(id: Int) = database.getMatchById(id)

}