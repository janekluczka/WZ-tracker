package com.example.wz_tracker.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface WzDao {
    @Insert
    fun insertMatch(match: Match)

    @Query("SELECT * from game_table WHERE id = :id")
    fun getMatchById(id: Int): Match?

    @Query("SELECT * from game_table")
    fun getAllMatches(): List<Match>

    @Query("DELETE FROM game_table")
    fun deleteAllMatches()
}